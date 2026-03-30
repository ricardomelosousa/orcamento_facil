package com.orcamento.orcamentofacilwatch.presentation.di

import android.content.Context
import com.orcamento.orcamentofacilwatch.presentation.domain.WearBudgetRepository
import com.orcamento.orcamentofacilwatch.presentation.domain.WearExpenseItem
import com.orcamento.orcamentofacilwatch.presentation.domain.WearPeriodSummary
import com.orcamento.orcamentofacilwatch.presentation.repo.WearBudgetRepositoryImpl


class WearContainer(
    context: Context
) {
//    val wearBudgetRepository: WearBudgetRepositoryImpl =
//        WearBudgetRepositoryImpl(context)
}


interface WearBudgetRepository {
    suspend fun getCurrentPeriodSummary(): WearPeriodSummary?
    suspend fun getRecentHistory(periodId: Long): List<WearExpenseItem>
}

class WearBudgetRepositoryImpl2(
    private val context: Context
) : WearBudgetRepository {

    override suspend fun getCurrentPeriodSummary(): WearPeriodSummary? {
        return WearPeriodSummary(
            periodId = 1L,
            label = "15/03 até 09/04",
            totalLimit = 2000.0,
            spent = 350.0,
            remaining = 1650.0
        )
    }

    override suspend fun getRecentHistory(periodId: Long): List<WearExpenseItem> {
        return listOf(
            WearExpenseItem(
                id = 1L,
                periodId = periodId,
                typeId = 1L,
                typeName = "Cartão Santander",
                amount = 350.0,
                expenseDate = "2026-03-26",
                description = "Compra exemplo"
            )
        )
    }

    override suspend fun addExpense(
        typeId: Long,
        amount: Double,
        expenseDate: String,
        description: String
    ) {
        TODO("Not yet implemented")
    }
}


