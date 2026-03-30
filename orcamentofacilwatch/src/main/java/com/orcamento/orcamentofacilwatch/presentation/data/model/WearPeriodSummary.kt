package com.orcamento.orcamentofacilwatch.presentation.data.model

@kotlinx.serialization.Serializable
data class WearPeriodSummary(
    val periodId: Long,
    val label: String,
    val totalLimit: Double,
    val spent: Double,
    val remaining: Double
)