package com.orcamento.orcamentofacilwatch.presentation.ui.ViewModel

import androidx.lifecycle.viewModelScope
import com.orcamento.orcamentofacilwatch.presentation.domain.WearBudgetRepository
import com.orcamento.orcamentofacilwatch.presentation.ui.home.WearHomeUiState
import kotlinx.coroutines.launch

class WearHomeViewModel(
    private val repository: WearBudgetRepository
) : androidx.lifecycle.ViewModel() {

    private val _uiState = kotlinx.coroutines.flow.MutableStateFlow<WearHomeUiState>(WearHomeUiState.Loading)
    val uiState: kotlinx.coroutines.flow.StateFlow<WearHomeUiState> = _uiState

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            val summary = repository.getCurrentPeriodSummary()
            _uiState.value = if (summary == null) {
                WearHomeUiState.Empty
            } else {
                WearHomeUiState.Success(summary)
            }
        }
    }
}