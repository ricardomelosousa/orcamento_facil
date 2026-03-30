package com.orcamento.orcamentofacil.wear

import com.orcamento.orcamentofacil.domain.BudgetRepository
import com.orcamento.orcamentofacil.domain.ExpenseInput

class MobileWearBridgeService(
    private val repository: BudgetRepository
) {
    suspend fun getCurrentSummary(): WearPeriodSummary? {
        val today = java.time.LocalDate.now().toString()
        val period = repository.findCurrentPeriod(today) ?: return null
        val spent = repository.getExpansePeriodSpent(period[0].id)

        return WearPeriodSummary(
            periodId = period[0].id,
            label = period[0].label,
            totalLimit = period[0].totalLimit,
            spent = spent,
            remaining = period[0].totalLimit - spent
        )
    }

    suspend fun getPeriodHistory(periodId: Long): List<WearExpenseItem> {
        val today = java.time.LocalDate.now().toString()

        return repository.findCurrentPeriod(today).map {
            WearExpenseItem(
                id = it.id,
                periodId = it.id,
                typeId = it.templateId,
                typeName = "Tipo ${it.templateId}",
                amount = it.totalLimit,
                expenseDate = it.startDate,
                description = it.label
            )
        }
    }

    suspend fun addExpense(request: AddExpenseRequest) {
        repository.saveExpense(
            ExpenseInput(
                typeId = request.typeId,
                amount = request.amount,
                expenseDate = request.expenseDate,
                description = request.description
            )
        )
    }
}