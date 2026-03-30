package com.orcamento.orcamentofacilwatch.presentation.domain

data class WearPeriodSummary(
    val periodId: Long,
    val label: String,
    val totalLimit: Double,
    val spent: Double,
    val remaining: Double
)

data class WearExpenseItem(
    val id: Long,
    val periodId: Long,
    val typeId: Long,
    val typeName: String,
    val amount: Double,
    val expenseDate: String,
    val description: String
)

data class WearExpenseType(
    val id: Long,
    val name: String
)