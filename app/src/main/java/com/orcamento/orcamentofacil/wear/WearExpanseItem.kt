package com.orcamento.orcamentofacil.wear

import kotlinx.serialization.Serializable

@Serializable
data class WearPeriodSummary(
    val periodId: Long,
    val label: String,
    val totalLimit: Double,
    val spent: Double,
    val remaining: Double
)

@Serializable
data class WearExpenseItem(
    val id: Long,
    val periodId: Long,
    val typeId: Long,
    val typeName: String,
    val amount: Double,
    val expenseDate: String,
    val description: String
)



@Serializable
data class AddExpenseRequest(
    val typeId: Long,
    val amount: Double,
    val expenseDate: String,
    val description: String
)