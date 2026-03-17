package com.orcamento.orcamentofacil.domain

data class BudgetTemplateInput(
    val id: Long? = null,
    val name: String,
    val startDayOfMonth: Int,
    val endDayOfMonth: Int,
    val totalLimit: Double,
    val autoRenew: Boolean,
    val types: List<String>
)

data class ExpenseInput(
    val typeId: Long,
    val amount: Double,
    val expenseDate: String,
    val description: String = ""
)

data class PeriodSummary(
    val periodId: Long,
    val templateId: Long,
    val name: String,
    val label: String,
    val startDate: String,
    val endDate: String,
    val totalLimit: Double,
    val spent: Double,
    val remaining: Double,
    val expenseCount: Int
)
