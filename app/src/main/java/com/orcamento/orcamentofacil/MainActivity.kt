package com.orcamento.orcamentofacil

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.content.ContextCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.orcamento.orcamentofacil.notifications.NotificationHelper
import com.orcamento.orcamentofacil.ui.navigation.AppNavHost
import com.orcamento.orcamentofacil.workers.DailyBudgetWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NotificationHelper.createChannel(this)
        requestNotificationPermissionIfNeeded()

        //Para debug remova o !
        if (!BuildConfig.DEBUG) {
            val request2 = OneTimeWorkRequestBuilder<DailyBudgetWorker>().build()
            WorkManager.getInstance(this).enqueue(request2)
            Log.d("WORK_DEBUG", "Rodando worker IMEDIATO (DEBUG)")
        } else {
            val now = java.time.LocalDateTime.now()
            val nextRun = now.withHour(8).withMinute(0).withSecond(0).withNano(0)
                .let { if (it.isBefore(now)) it.plusDays(1) else it }

            val initialDelay = java.time.Duration.between(now, nextRun).toMillis()

            val request = PeriodicWorkRequestBuilder<DailyBudgetWorker>(
                1, TimeUnit.DAYS
            )
                .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                .build()

            WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "daily_budget_notification",
                ExistingPeriodicWorkPolicy.UPDATE,
                request
            )
        }
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                AppNavHost(appContainer = (application as OrcamentoFacilApp).container,
                    openHistory = intent?.getBooleanExtra("openHistory", false) ?: false,
                    notificationPeriodId = intent?.getLongExtra("periodId", -1L) ?: -1L)
            }
        }
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}

object BuildConfig {
    const val DEBUG = true
}
