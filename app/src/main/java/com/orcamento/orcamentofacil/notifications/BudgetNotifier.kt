package com.orcamento.orcamentofacil.notifications

import android.Manifest
import android.R.mipmap
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.orcamento.orcamentofacil.MainActivity
import com.orcamento.orcamentofacil.R
import java.text.NumberFormat
import java.util.Locale

object BudgetNotifier {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showNotification(
        context: Context,
        id: Int,
        title: String,
        message: String
    ) {

//        val intent = Intent(context, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
//            putExtra("openHistory", true)
//            putExtra("periodId", id)
//        }
//
//        val pendingIntent = PendingIntent.getActivity(
//            context,
//            id.toInt(),
//            intent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//
//        val builder = NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
//            .setSmallIcon(R.mipmap.ic_orca)
//            .setContentTitle(title)
//            .setContentText(message)
//            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setAutoCancel(true)
//            .setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE)
//
//        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        manager.notify(id, builder.build())

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("openHistory", true)
            putExtra("periodId", id)
        }
        //val brl = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        val pendingIntent = PendingIntent.getActivity(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, "orcamento_facil_channel")
            .setSmallIcon(R.mipmap.ic_orca)
            .setContentTitle("Gasto lançado")
            .setContentText("Restante: $message")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Gasto lançado com sucesso. Restante do período: $message")
            )
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE)
            .build()

        NotificationManagerCompat.from(context).notify(id, notification)


    }
}