package com.orcamento.orcamentofacilwatch.presentation.data.model

@kotlinx.serialization.Serializable
data class AddExpenseRequest(
    val typeId: Long,
    val amount: Double,
    val expenseDate: String,
    val description: String
)