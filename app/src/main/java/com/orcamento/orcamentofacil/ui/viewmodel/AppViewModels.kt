package com.orcamento.orcamentofacil.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.orcamento.orcamentofacil.domain.BudgetRepository
import com.orcamento.orcamentofacil.domain.BudgetTemplateInput
import com.orcamento.orcamentofacil.domain.ExpenseInput
import com.orcamento.orcamentofacil.notifications.ExpenseUiEvent

import com.orcamento.orcamentofacil.util.DateUtils
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

import java.time.format.DateTimeFormatter
import kotlin.collections.filter

private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

class HomeViewModel(private val repository: BudgetRepository) : ViewModel() {
    val periods = repository.observePeriodSummaries()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val periodsFilter = periods
        .map { list ->
            val today = LocalDate.now()

            list.filter { period ->
                val start = runCatching {
                    LocalDate.parse(period.startDate, formatter)
                }.getOrNull()

                val end = runCatching {
                    LocalDate.parse(period.endDate, formatter)
                }.getOrNull()

                period.spent > 0 &&
                        start != null &&
                        end != null &&
                        today in start..end
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    // it.spent > 0 && (now() >= LocalDate.parse(it.startDate, formatter) &&  now() <= LocalDate.parse(it.endDate, formatter) ) }
    val templates = repository.observeTemplates()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        viewModelScope.launch { repository.bootstrap() }
    }


}

class HistoryViewModel(private val repository: BudgetRepository) : ViewModel() {
    val periods = repository.observePeriodSummaries()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val templates = repository.observeTemplates()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        viewModelScope.launch { repository.bootstrap() }
    }
}


class TemplateFormViewModel(private val repository: BudgetRepository) : ViewModel() {
    private val _state = MutableStateFlow(TemplateFormState())
    val state = _state.asStateFlow()

    fun loadTemplate(templateId: Long) {
        viewModelScope.launch {
            val template = repository.getTemplateById(templateId) ?: return@launch
            _state.value = TemplateFormState(
                id = template.template.id,
                name = template.template.name,
                startDay = template.template.startDayOfMonth.toString(),
                endDay = template.template.endDayOfMonth.toString(),
                totalLimit = template.template.totalLimit.toString().replace('.', ','),
                autoRenew = template.template.autoRenew,
                typesText = template.types.joinToString("\n") { it.name }
            )
        }
    }

    fun updateName(value: String) = _state.update { it.copy(name = value) }
    fun updateStartDay(value: String) = _state.update { it.copy(startDay = value) }
    fun updateEndDay(value: String) = _state.update { it.copy(endDay = value) }
    fun updateLimit(value: String) = _state.update { it.copy(totalLimit = value) }
    fun updateAutoRenew(value: Boolean) = _state.update { it.copy(autoRenew = value) }
    fun updateTypesFromText(value: String) = _state.update { it.copy(typesText = value) }

    fun save(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val input = state.value.toInput() ?: run {
                _state.update { it.copy(error = "Preencha nome, dias válidos, valor e pelo menos um tipo.") }
                return@launch
            }
            repository.saveTemplate(input)
            onSuccess()
        }
    }
}

class ExpenseFormViewModel(private val repository: BudgetRepository) : ViewModel() {

    private val _events = MutableSharedFlow<ExpenseUiEvent>()
    val events = _events.asSharedFlow()
    private val _selectedTemplateId = MutableStateFlow<Long?>(null)
    private val _state =
        MutableStateFlow(ExpenseFormState(expenseDate = DateUtils.today().toString()))
    val state = _state.asStateFlow()

    val templates = repository.observeTemplates()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val availableTypes = combine(templates, _selectedTemplateId) { list, selectedId ->
        list.firstOrNull { it.template.id == selectedId }?.types.orEmpty()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun selectTemplate(templateId: Long) {
        _selectedTemplateId.value = templateId
        _state.update { it.copy(selectedTemplateId = templateId, selectedTypeId = null) }
    }

    fun selectType(typeId: Long) = _state.update { it.copy(selectedTypeId = typeId) }
    fun updateAmount(value: String) = _state.update { it.copy(amount = value) }
    fun updateDate(value: String) = _state.update { it.copy(expenseDate = value) }
    fun updateDescription(value: String) = _state.update { it.copy(description = value) }


    fun save(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val current = state.value
            val amount = current.amount.replace(".", "").replace(',', '.').toDoubleOrNull()
            val typeId = current.selectedTypeId
            if (amount == null || amount <= 0.0 || typeId == null || current.expenseDate.isBlank()) {
                _state.update { it.copy(error = "Informe tipo, data e valor válido.") }
                return@launch
            }
            repository.saveExpense(
                ExpenseInput(
                    typeId = typeId,
                    amount = amount,
                    expenseDate = current.expenseDate,
                    description = current.description
                )
            )
            onSuccess()

            val matching = repository.getExpensesPeriod(current.expenseDate, typeId)

            val spent = repository.getExpansePeriodSpent(matching.id)
            val spentValue = spent.toString().toDoubleOrNull() ?: 0.0
            val remaining = matching.totalLimit - spentValue
            try {
                _events.emit(ExpenseUiEvent.ExpenseSaved(remaining))

            } catch (e: Exception) {
                Log.e("AppViewModels", "Erro ao emitir evento", e)
            }
        }


    }


}

class PeriodDetailViewModel(repository: BudgetRepository, periodId: Long) : ViewModel() {
    val detail = repository.observePeriodDetails(periodId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
}

data class TemplateFormState(
    val id: Long? = null,
    val name: String = "",
    val startDay: String = "15",
    val endDay: String = "09",
    val totalLimit: String = "2000,00",
    val autoRenew: Boolean = true,
    val typesText: String = "Cartão crédito Santander\nDébito\nCheque especial",
    val error: String? = null
) {
    fun toInput(): BudgetTemplateInput? {
        val start = startDay.toIntOrNull()
        val end = endDay.toIntOrNull()
        val limit = totalLimit.replace(".", "").replace(',', '.').toDoubleOrNull()
        val types = typesText.lines().map { it.trim() }.filter { it.isNotBlank() }
        if (name.isBlank() || start !in 1..28 || end !in 1..28 || limit == null || types.isEmpty()) return null
        return BudgetTemplateInput(
            id = id,
            name = name,
            startDayOfMonth = start ?: 0,
            endDayOfMonth = end ?: 0,
            totalLimit = limit,
            autoRenew = autoRenew,
            types = types
        )
    }
}

data class ExpenseFormState(
    val selectedTemplateId: Long? = null,
    val selectedTypeId: Long? = null,
    val amount: String = "",
    val expenseDate: String,
    val description: String = "",
    val error: String? = null
)

@Suppress("UNCHECKED_CAST")
class AppViewModelFactory(
    private val repository: BudgetRepository,
    private val periodId: Long? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository) as T
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> HistoryViewModel(repository) as T
            modelClass.isAssignableFrom(TemplateFormViewModel::class.java) -> TemplateFormViewModel(
                repository
            ) as T

            modelClass.isAssignableFrom(ExpenseFormViewModel::class.java) -> ExpenseFormViewModel(
                repository
            ) as T

            modelClass.isAssignableFrom(PeriodDetailViewModel::class.java) && periodId != null -> PeriodDetailViewModel(
                repository,
                periodId
            ) as T

            else -> error("ViewModel não suportada")
        }
    }
}

private inline fun <T> MutableStateFlow<T>.update(block: (T) -> T) {
    value = block(value)
}



