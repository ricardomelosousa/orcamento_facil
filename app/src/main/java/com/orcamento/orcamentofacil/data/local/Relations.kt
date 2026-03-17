package com.orcamento.orcamentofacil.data.local

import androidx.room.Embedded
import androidx.room.Relation

data class TemplateWithTypes(
    @Embedded val template: BudgetTemplateEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "templateId"
    )
    val types: List<ExpenseTypeEntity>
)

data class ExpenseEntryWithType(
    @Embedded val entry: ExpenseEntryEntity,
    @Relation(
        parentColumn = "typeId",
        entityColumn = "id"
    )
    val type: ExpenseTypeEntity
)

data class PeriodWithExpenses(
    @Embedded val period: BudgetPeriodEntity,
    @Relation(
        entity = ExpenseEntryEntity::class,
        parentColumn = "id",
        entityColumn = "periodId"
    )
    val expenses: List<ExpenseEntryWithType>
)
