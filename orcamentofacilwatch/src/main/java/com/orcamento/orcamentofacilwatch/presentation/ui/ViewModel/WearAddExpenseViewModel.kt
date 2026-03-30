package com.orcamento.orcamentofacilwatch.presentation.ui.ViewModel

import androidx.lifecycle.viewModelScope
import com.orcamento.orcamentofacilwatch.presentation.domain.WearBudgetRepository
import com.orcamento.orcamentofacilwatch.presentation.repo.WearBudgetRepositoryImpl
import com.orcamento.orcamentofacilwatch.presentation.ui.expense.WearAddExpenseUiState
import kotlinx.coroutines.launch

class WearAddExpenseViewModel(
    private val repository: WearBudgetRepositoryImpl
) : androidx.lifecycle.ViewModel() {

    private val _uiState = kotlinx.coroutines.flow.MutableStateFlow(WearAddExpenseUiState())
    val uiState: kotlinx.coroutines.flow.StateFlow<WearAddExpenseUiState> = _uiState

    fun loadTypes() {
        // primeira versão: pode usar tipos sincronizados do período atual
    }

    fun selectType(typeId: Long) {
        _uiState.value = _uiState.value.copy(selectedTypeId = typeId)
    }

    fun saveExampleExpense() {
        viewModelScope.launch {
            val typeId = _uiState.value.selectedTypeId ?: return@launch
            repository.addExpense(
                typeId = typeId,
                amount = 10.0,
                expenseDate = java.time.LocalDate.now().toString(),
                description = "Lançado pelo relógio"
            )
        }
    }
}