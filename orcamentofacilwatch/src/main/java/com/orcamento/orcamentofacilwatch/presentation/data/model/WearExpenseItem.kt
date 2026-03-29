package com.orcamento.orcamentofacilwatch.presentation.data.model

@kotlinx.serialization.Serializable
data class WearExpenseItem(
    val id: Long,
    val periodId: Long,
    val typeId: Long,
    val typeName: String,
    val amount: Double,
    val expenseDate: String,
    val description: String
)