package com.orcamento.orcamentofacil.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        BudgetTemplateEntity::class,
        ExpenseTypeEntity::class,
        BudgetPeriodEntity::class,
        ExpenseEntryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDao
}
