package com.orcamento.orcamentofacilwatch.presentation.domain

import com.orcamento.core_model.ExpenseEntryEntity


interface BudgetRepository {
    suspend fun findCurrentPeriod(today: String): BudgetPeriod?
    suspend fun getSpentNow(periodId: Long): Double
    suspend fun getExpensesByPeriod(periodId: Long): List<ExpenseEntryEntity>
    suspend fun getPeriodById(periodId: Long): BudgetPeriod?
    suspend fun insertExpense(expense: ExpenseEntryEntity): Long
}

annotation class BudgetPeriod

interface WearBudgetRepository {
    suspend fun getCurrentPeriodSummary(): WearPeriodSummary?
    suspend fun getRecentHistory(periodId: Long): List<WearExpenseItem>
    suspend fun addExpense(typeId: Long, amount: Double, expenseDate: String, description: String)
}