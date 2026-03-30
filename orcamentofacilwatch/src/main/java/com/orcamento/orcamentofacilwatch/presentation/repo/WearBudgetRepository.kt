package com.orcamento.orcamentofacilwatch.presentation.repo

import com.orcamento.orcamentofacilwatch.presentation.data.model.AddExpenseRequest
import com.orcamento.orcamentofacilwatch.presentation.data.model.WearDataLayerClient
import com.orcamento.orcamentofacilwatch.presentation.domain.WearExpenseItem
import kotlinx.coroutines.flow.Flow

interface WearBudgetRepository {
    suspend fun getRecentHistory(periodId: Long): List<WearExpenseItem>
    suspend fun addExpense(typeId: Long, amount: Double, expenseDate: String, description: String)
    fun observeCurrentSummary(): Flow<com.orcamento.orcamentofacilwatch.presentation.data.model.WearPeriodSummary?>
}

class WearBudgetRepositoryImpl(
    private val client: WearDataLayerClient
)  {

     fun observeCurrentSummary() = client.observeCurrentSummary()

     suspend fun getRecentHistory(periodId: Long): List<WearExpenseItem> {
        return client.requestPeriodHistory(periodId)
    }

     suspend fun addExpense(
        typeId: Long,
        amount: Double,
        expenseDate: String,
        description: String
    ) {
        client.addExpense(
            AddExpenseRequest(
                typeId = typeId,
                amount = amount,
                expenseDate = expenseDate,
                description = description
            )
        )
    }
}