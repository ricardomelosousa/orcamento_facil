package com.orcamento.orcamentofacilwatch.presentation.ui.ViewModel

import androidx.lifecycle.viewModelScope
import com.orcamento.orcamentofacilwatch.presentation.domain.WearBudgetRepository
import com.orcamento.orcamentofacilwatch.presentation.domain.WearExpenseItem
import com.orcamento.orcamentofacilwatch.presentation.repo.WearBudgetRepositoryImpl
import kotlinx.coroutines.launch

class WearHistoryViewModel(
    private val repository: WearBudgetRepositoryImpl
) : androidx.lifecycle.ViewModel() {

    private val _items = kotlinx.coroutines.flow.MutableStateFlow<List<WearExpenseItem>>(emptyList())
    val items: kotlinx.coroutines.flow.StateFlow<List<WearExpenseItem>> = _items

    fun load(periodId: Long) {
        viewModelScope.launch {
            _items.value = repository.getRecentHistory(periodId)
        }
    }
}