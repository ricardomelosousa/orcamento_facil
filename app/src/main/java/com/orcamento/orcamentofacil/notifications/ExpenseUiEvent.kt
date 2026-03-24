package com.orcamento.orcamentofacil.notifications

sealed class ExpenseUiEvent {
    data class ExpenseSaved(val remaining: Double) : ExpenseUiEvent()
}
