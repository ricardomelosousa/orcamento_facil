package com.orcamento.orcamentofacil

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.room.util.TableInfo
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.orcamento.orcamentofacil.notifications.NotificationHelper
import com.orcamento.orcamentofacil.ui.navigation.AppNavHost
import com.orcamento.orcamentofacil.workers.DailyBudgetWorker
import java.util.concurrent.TimeUnit
import java.util.jar.Manifest

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
            val request = PeriodicWorkRequestBuilder<DailyBudgetWorker>(
                1, TimeUnit.DAYS
            ).build()

            WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "daily_budget_notification",
                ExistingPeriodicWorkPolicy.UPDATE,
                request
            )
        }
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                AppNavHost(appContainer = (application as OrcamentoFacilApp).container)
                //Teste()
            }
        }
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
//    @Preview(showBackground = true)
//    @Composable
//    fun Teste(){
//        Column {
//            Box(modifier =  Modifier.height(30.dp).background(Color.Blue).width(50.dp)){
//                Image(painter = painterResource(id = R.drawable.orca_facil),
//                    contentDescription = "Imagem do produto")
//            }
//        }
//    }


}

object BuildConfig {
    const val DEBUG = true
}
