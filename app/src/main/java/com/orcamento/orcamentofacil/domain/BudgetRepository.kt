package com.orcamento.orcamentofacil.domain

import com.orcamento.orcamentofacil.data.local.BudgetDao
import com.orcamento.orcamentofacil.data.local.BudgetPeriodEntity
import com.orcamento.orcamentofacil.data.local.BudgetTemplateEntity
import com.orcamento.orcamentofacil.data.local.ExpenseEntryEntity
import com.orcamento.orcamentofacil.data.local.ExpenseTypeEntity
import com.orcamento.orcamentofacil.util.DateUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class BudgetRepository(private val dao: BudgetDao) {

    fun observePeriodSummaries(): Flow<List<PeriodSummary>> {
        return dao.observePeriods().combine(dao.observeTemplates()) { periods, templates ->
            val templateMap = templates.associateBy { it.template.id }
            periods.map { period ->
                val spent = dao.observePeriodSpent(period.id).first()
                val template = templateMap[period.templateId]?.template
                PeriodSummary(
                    periodId = period.id,
                    templateId = period.templateId,
                    name = template?.name ?: "Período",
                    label = period.label,
                    startDate = period.startDate,
                    endDate = period.endDate,
                    totalLimit = period.totalLimit,
                    spent = spent,
                    remaining = period.totalLimit - spent,
                    expenseCount = dao.observePeriodDetails(period.id).first()?.expenses?.size ?: 0
                )
            }
        }
    }

    fun observeTemplates() = dao.observeTemplates()

     suspend fun getExpansePeriodSpent(periodId: Long): Double {
         val spent = dao.observePeriodSpent(periodId).first()
        return spent
    }
    fun observePeriodDetails(periodId: Long) = dao.observePeriodDetails(periodId)

    suspend fun getTemplateById(templateId: Long) = dao.getTemplate(templateId)

    suspend fun bootstrap() {
        ensurePeriodsGenerated()
    }

    suspend fun saveTemplate(input: BudgetTemplateInput) {
        val templateId = if (input.id == null) {
            dao.insertTemplate(
                BudgetTemplateEntity(
                    name = input.name,
                    startDayOfMonth = input.startDayOfMonth,
                    endDayOfMonth = input.endDayOfMonth,
                    totalLimit = input.totalLimit,
                    autoRenew = input.autoRenew
                )
            )
        } else {
            val existing = dao.getTemplate(input.id)?.template ?: return
            dao.updateTemplate(
                existing.copy(
                    name = input.name,
                    startDayOfMonth = input.startDayOfMonth,
                    endDayOfMonth = input.endDayOfMonth,
                    totalLimit = input.totalLimit,
                    autoRenew = input.autoRenew
                )
            )
            input.id
        }

        dao.deleteTypesByTemplate(templateId)
        dao.insertTypes(
            input.types
                .map { it.trim() }
                .filter { it.isNotBlank() }
                .distinct()
                .map { ExpenseTypeEntity(templateId = templateId, name = it) }
        )

        ensurePeriodsGenerated(templateId)
    }

    suspend fun saveExpense(input: ExpenseInput) {
        val type = dao.getExpenseTypeById(input.typeId) ?: return
        val expenseDate = DateUtils.parse(input.expenseDate)
        val periods = dao.getPeriodsContainingDate(input.expenseDate)
        val matching = periods.firstOrNull { it.templateId == type.templateId }
            ?: ensurePeriodForDate(type.templateId, expenseDate)

        dao.insertExpense(
            ExpenseEntryEntity(
                periodId = matching.id,
                typeId = input.typeId,
                amount = input.amount,
                expenseDate = input.expenseDate,
                description = input.description
            )
        )
    }

    suspend fun getExpensesPeriod(expenseDate: String, typeId: Long):  BudgetPeriodEntity {

        val type = dao.getExpenseTypeById(typeId)
        val periods = dao.getPeriodsContainingDate(expenseDate)
        val matching = periods.firstOrNull { it.templateId == type?.templateId }
            ?: ensurePeriodForDate(type?.templateId ?: 0, DateUtils.parse(expenseDate))

        return matching
    }

    private suspend fun ensurePeriodsGenerated(templateId: Long? = null) {
        val templates = dao.observeTemplates().first()
            .filter { templateId == null || it.template.id == templateId }
        val today = DateUtils.today()
        templates.forEach { templateWithTypes ->
            val template = templateWithTypes.template
            val refs = buildList {
                val current = today.year to today.monthNumber
                add(current)
                add(DateUtils.monthBack(today))
                add(DateUtils.monthForward(today.year, today.monthNumber))
            }
            refs.forEachIndexed { index, (year, month) ->
                if (template.autoRenew || index < 2) {
                    upsertPeriod(template, year, month)
                }
            }
        }
    }

    private suspend fun ensurePeriodForDate(templateId: Long, date: kotlinx.datetime.LocalDate): BudgetPeriodEntity {
        val template = dao.getTemplate(templateId)?.template ?: error("Template não encontrado")
        val current = upsertPeriod(template, date.year, date.monthNumber)
        val start = DateUtils.parse(current.startDate)
        val end = DateUtils.parse(current.endDate)
        return when {
            date >= start && date <= end -> current
            date < start -> {
                val (prevYear, prevMonth) = DateUtils.monthBack(date)
                upsertPeriod(template, prevYear, prevMonth)
            }
            else -> {
                val (nextYear, nextMonth) = DateUtils.monthForward(date.year, date.monthNumber)
                upsertPeriod(template, nextYear, nextMonth)
            }
        }
    }

    private suspend fun upsertPeriod(template: BudgetTemplateEntity, year: Int, month: Int): BudgetPeriodEntity {
        val (start, end) = DateUtils.createPeriodRange(year, month, template.startDayOfMonth, template.endDayOfMonth)
        val entity = BudgetPeriodEntity(
            id = dao.findPeriodByReference(template.id, year, month)?.id ?: 0,
            templateId = template.id,
            referenceYear = year,
            referenceMonth = month,
            label = DateUtils.label(start, end),
            startDate = DateUtils.format(start),
            endDate = DateUtils.format(end),
            totalLimit = template.totalLimit
        )
        val existing = dao.findPeriodByReference(template.id, year, month)

        return if (existing == null) {
            val newId = dao.insertPeriod(entity.copy(id = 0))
            entity.copy(id = newId)
        } else {
            val updated = entity.copy(id = existing.id)
            dao.updatePeriod(updated)
            updated
        }
//        val id = dao.insertOrReplacePeriod(entity)
//        return entity.copy(id = if (entity.id == 0L) id else entity.id)
    }

    fun observeTypesForTemplate(templateId: Long): Flow<List<ExpenseTypeEntity>> {
        return dao.observeTemplates().map { templates ->
            templates.firstOrNull { it.template.id == templateId }?.types.orEmpty()
        }
    }
}
