package com.orcamento.orcamentofacil.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Transaction
    @Query("SELECT * FROM budget_templates WHERE isActive = 1 ORDER BY name")
    fun observeTemplates(): Flow<List<TemplateWithTypes>>

    @Transaction
    @Query("SELECT * FROM budget_templates WHERE id = :templateId")
    suspend fun getTemplate(templateId: Long): TemplateWithTypes?

    @Insert
    suspend fun insertTemplate(template: BudgetTemplateEntity): Long

    @Update
    suspend fun updateTemplate(template: BudgetTemplateEntity)

    @Query("DELETE FROM expense_types WHERE templateId = :templateId")
    suspend fun deleteTypesByTemplate(templateId: Long)

    @Insert
    suspend fun insertTypes(types: List<ExpenseTypeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplacePeriod(period: BudgetPeriodEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPeriod(entity: BudgetPeriodEntity): Long

    @Update
    suspend fun updatePeriod(entity: BudgetPeriodEntity)

    @Transaction
    @Query("SELECT * FROM budget_periods ORDER BY startDate DESC")
    fun observePeriods(): Flow<List<BudgetPeriodEntity>>

    @Transaction
    @Query("SELECT * FROM budget_periods WHERE id = :periodId")
    fun observePeriodDetails(periodId: Long): Flow<PeriodWithExpenses?>

    @Query("SELECT * FROM budget_periods WHERE templateId = :templateId AND referenceYear = :year AND referenceMonth = :month LIMIT 1")
    suspend fun findPeriodByReference(templateId: Long, year: Int, month: Int): BudgetPeriodEntity?

    @Query("SELECT * FROM expense_types WHERE id = :typeId LIMIT 1")
    suspend fun getExpenseTypeById(typeId: Long): ExpenseTypeEntity?

    @Insert
    suspend fun insertExpense(entry: ExpenseEntryEntity): Long

    @Query("SELECT COALESCE(SUM(amount), 0) FROM expense_entries WHERE periodId = :periodId")
    fun observePeriodSpent(periodId: Long): Flow<Double>

    @Transaction
    @Query("SELECT * FROM budget_periods WHERE startDate <= :date AND endDate >= :date")
    suspend fun getPeriodsContainingDate(date: String): List<BudgetPeriodEntity>

    @Transaction
    @Query("SELECT * FROM budget_periods WHERE templateId = :templateId ORDER BY startDate DESC")
    fun observePeriodsByTemplate(templateId: Long): Flow<List<BudgetPeriodEntity>>
}
