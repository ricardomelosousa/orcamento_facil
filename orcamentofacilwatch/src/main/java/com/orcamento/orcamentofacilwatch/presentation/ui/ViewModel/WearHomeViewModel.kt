package com.orcamento.orcamentofacilwatch.presentation.ui.ViewModel

import androidx.lifecycle.viewModelScope
import com.orcamento.orcamentofacilwatch.presentation.repo.WearBudgetRepositoryImpl

import com.orcamento.orcamentofacilwatch.presentation.ui.home.WearHomeUiState
import kotlinx.coroutines.launch

class WearHomeViewModel(
    private val repository: WearBudgetRepositoryImpl
) : androidx.lifecycle.ViewModel() {

    private val _uiState = kotlinx.coroutines.flow.MutableStateFlow<WearHomeUiState>(WearHomeUiState.Loading)
    val uiState: kotlinx.coroutines.flow.StateFlow<WearHomeUiState> = _uiState

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            repository.observeCurrentSummary().collect { summary ->
                _uiState.value = if (summary == null) {
                    WearHomeUiState.Empty
                } else {
                    WearHomeUiState.Success(summary)
                }
            }
        }
    }
}