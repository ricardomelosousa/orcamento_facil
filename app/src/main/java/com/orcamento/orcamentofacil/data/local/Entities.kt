package com.orcamento.orcamentofacil.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "budget_templates")
data class BudgetTemplateEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val startDayOfMonth: Int,
    val endDayOfMonth: Int,
    val totalLimit: Double,
    val autoRenew: Boolean = true,
    val isActive: Boolean = true
)

@Entity(
    tableName = "expense_types",
    foreignKeys = [
        ForeignKey(
            entity = BudgetTemplateEntity::class,
            parentColumns = ["id"],
            childColumns = ["templateId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("templateId")]
)
data class ExpenseTypeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val templateId: Long,
    val name: String
)

@Entity(
    tableName = "budget_periods",
    foreignKeys = [
        ForeignKey(
            entity = BudgetTemplateEntity::class,
            parentColumns = ["id"],
            childColumns = ["templateId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["templateId", "referenceYear", "referenceMonth"], unique = true)]
)
data class BudgetPeriodEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val templateId: Long,
    val referenceYear: Int,
    val referenceMonth: Int,
    val label: String,
    val startDate: String,
    val endDate: String,
    val totalLimit: Double
)

@Entity(
    tableName = "expense_entries",
    foreignKeys = [
        ForeignKey(
            entity = BudgetPeriodEntity::class,
            parentColumns = ["id"],
            childColumns = ["periodId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExpenseTypeEntity::class,
            parentColumns = ["id"],
            childColumns = ["typeId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index("periodId"), Index("typeId")]
)
data class ExpenseEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val periodId: Long,
    val typeId: Long,
    val amount: Double,
    val expenseDate: String,
    val description: String = ""
)
