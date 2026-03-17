package com.orcamento.orcamentofacil.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

object DateUtils {
    fun today(): LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    fun parse(date: String): LocalDate = LocalDate.parse(date)

    fun format(date: LocalDate): String = date.toString()

    fun createPeriodRange(referenceYear: Int, referenceMonth: Int, startDay: Int, endDay: Int): Pair<LocalDate, LocalDate> {
        val start = LocalDate(referenceYear, referenceMonth, startDay)
        val end = if (endDay >= startDay) {
            LocalDate(referenceYear, referenceMonth, endDay)
        } else {
            val nextMonth = if (referenceMonth == 12) 1 else referenceMonth + 1
            val nextYear = if (referenceMonth == 12) referenceYear + 1 else referenceYear
            LocalDate(nextYear, nextMonth, endDay)
        }
        return start to end
    }

    fun label(start: LocalDate, end: LocalDate): String = "${start.dayOfMonth.toString().padStart(2, '0')}/${start.monthNumber.toString().padStart(2, '0')} a ${end.dayOfMonth.toString().padStart(2, '0')}/${end.monthNumber.toString().padStart(2, '0')}"

    fun monthBack(date: LocalDate): Pair<Int, Int> {
        val month = if (date.monthNumber == 1) 12 else date.monthNumber - 1
        val year = if (date.monthNumber == 1) date.year - 1 else date.year
        return year to month
    }

    fun monthForward(year: Int, month: Int): Pair<Int, Int> {
        return if (month == 12) year + 1 to 1 else year to month + 1
    }
}
