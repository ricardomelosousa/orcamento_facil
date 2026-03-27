package com.orcamento.orcamentofacil.data.local

import android.content.Context
import androidx.room.Room
import com.orcamento.orcamentofacil.domain.BudgetRepository


class AppContainer(context: Context) {
    private val database = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "orcamento_facil.db"
    ).build()

    val repository: BudgetRepository = BudgetRepository(database.budgetDao())
}
