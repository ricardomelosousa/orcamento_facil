package com.orcamento.core_model

import androidx.room3.Entity



data class BudgetTemplateEntity(
  val id: Long = 0,
    val name: String,
    val startDayOfMonth: Int,
    val endDayOfMonth: Int,
    val totalLimit: Double,
    val autoRenew: Boolean = true,
    val isActive: Boolean = true
)


data class ExpenseTypeEntity(
   val id: Long = 0,
    val templateId: Long,
    val name: String
)


data class BudgetPeriodEntity(
    val id: Long = 0,
    val templateId: Long,
    val referenceYear: Int,
    val referenceMonth: Int,
    val label: String,
    val startDate: String,
    val endDate: String,
    val totalLimit: Double
)


data class ExpenseEntryEntity(
   val periodId: Long,
    val typeId: Long,
    val amount: Double,
    val expenseDate: String,
    val description: String = ""
)
