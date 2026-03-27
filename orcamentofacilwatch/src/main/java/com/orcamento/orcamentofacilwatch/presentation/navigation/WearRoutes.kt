package com.orcamento.orcamentofacilwatch.presentation.navigation

sealed class WearRoutes(val route: String) {
    data object Home : WearRoutes("home")
    data object AddExpense : WearRoutes("add_expense")
    data object History : WearRoutes("history/{periodId}") {
        fun create(periodId: Long) = "history/$periodId"
    }
}