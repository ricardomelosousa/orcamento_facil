package com.orcamento.orcamentofacil.wear

import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import com.orcamento.orcamentofacil.OrcamentoFacilApp
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.encodeToString

class MobileWearListenerService : com.google.android.gms.wearable.WearableListenerService() {

    private val scope = kotlinx.coroutines.CoroutineScope(
        kotlinx.coroutines.SupervisorJob() + kotlinx.coroutines.Dispatchers.IO
    )

    override fun onMessageReceived(messageEvent: com.google.android.gms.wearable.MessageEvent) {
        val app = application as OrcamentoFacilApp
        val bridge = MobileWearBridgeService(app.container.repository)

        when (messageEvent.path) {
            WearPaths.REQUEST_PERIOD_HISTORY -> {
                scope.launch {
                    val periodId = String(messageEvent.data).toLong()
                    val items = bridge.getPeriodHistory(periodId)
                    val json = kotlinx.serialization.json.Json.encodeToString(items)

                    com.google.android.gms.wearable.Wearable
                        .getMessageClient(applicationContext)
                        .sendMessage(
                            messageEvent.sourceNodeId,
                            WearPaths.RESPONSE_PERIOD_HISTORY,
                            json.toByteArray()
                        )
                }
            }

            WearPaths.ADD_EXPENSE -> {
                scope.launch {
                    val json = String(messageEvent.data)
                    val request = kotlinx.serialization.json.Json.decodeFromString<AddExpenseRequest>(json)
                    bridge.addExpense(request)
                    syncCurrentSummary(bridge)
                }
            }
        }
    }

    private suspend fun syncCurrentSummary(bridge: MobileWearBridgeService) {
        val summary = bridge.getCurrentSummary() ?: return
        val json = kotlinx.serialization.json.Json.encodeToString(summary)

        val request = PutDataMapRequest
            .create(WearPaths.CURRENT_SUMMARY)

        request.dataMap.putString("summary_json", json)
        request.dataMap.putLong("updated_at", System.currentTimeMillis())

        Wearable
            .getDataClient(applicationContext)
            .putDataItem(request.asPutDataRequest().setUrgent())
            .await()
    }
}