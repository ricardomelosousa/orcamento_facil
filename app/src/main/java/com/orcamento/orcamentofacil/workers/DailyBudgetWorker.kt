package com.orcamento.orcamentofacil.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.orcamento.orcamentofacil.data.local.AppDatabase
import com.orcamento.orcamentofacil.notifications.BudgetNotifier
import java.text.NumberFormat
import java.time.LocalDate
import java.util.Locale

class DailyBudgetWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {

        val db = AppDatabase.getDatabase(applicationContext)
        val dao = db.budgetDao()

        val today = java.time.LocalDate.now().toString()

        val currentPeriods = dao.findCurrentPeriod(today)

        if (currentPeriods != null) {
            for (currentPeriod in currentPeriods) {
                if (currentPeriod != null) {
                    val spent = dao.getSpentNow(currentPeriod.id)
                    val remaining = currentPeriod.totalLimit - spent
                    val temp = dao.getTemplate(currentPeriod.templateId)
                    val brl = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("pt", "BR"))

                    BudgetNotifier.showNotification(
                        context = applicationContext,
                        id = currentPeriod.id.toInt(),
                        title = "Lembrete Gastos - ${temp?.template?.name}",
                        message = "${brl.format(currentPeriod.totalLimit)} Restante : ${brl.format(remaining)}"
                    )
                }
            }
        }
        return Result.success()
    }
}