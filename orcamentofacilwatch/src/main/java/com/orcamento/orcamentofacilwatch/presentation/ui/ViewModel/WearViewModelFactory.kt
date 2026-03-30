package com.orcamento.orcamentofacilwatch.presentation.ui.ViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.orcamento.orcamentofacilwatch.presentation.repo.WearBudgetRepositoryImpl


class WearViewModelFactory(
    private val repository: WearBudgetRepositoryImpl
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when {

            modelClass.isAssignableFrom(WearHomeViewModel::class.java) -> {
                WearHomeViewModel(repository) as T
            }

            modelClass.isAssignableFrom(WearHistoryViewModel::class.java) -> {
                WearHistoryViewModel(repository) as T
            }

            modelClass.isAssignableFrom(WearAddExpenseViewModel::class.java) -> {
                WearAddExpenseViewModel(repository) as T
            }

            else -> throw IllegalArgumentException(
                "Unknown ViewModel class: ${modelClass.name}"
            )
        }
    }
}