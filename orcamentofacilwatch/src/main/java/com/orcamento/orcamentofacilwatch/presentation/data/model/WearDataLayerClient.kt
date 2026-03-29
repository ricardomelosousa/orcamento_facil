package com.orcamento.orcamentofacilwatch.presentation.data.model

import android.content.Context
import com.orcamento.orcamentofacilwatch.presentation.domain.WearExpenseItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class WearDataLayerClient(
    context: Context
) {
    private val dataClient = com.google.android.gms.wearable.Wearable.getDataClient(context)
    private val messageClient = com.google.android.gms.wearable.Wearable.getMessageClient(context)
    private val capabilityClient = com.google.android.gms.wearable.Wearable.getCapabilityClient(context)

    fun observeCurrentSummary(): kotlinx.coroutines.flow.Flow<WearPeriodSummary?> =
        kotlinx.coroutines.flow.callbackFlow {
            val listener = com.google.android.gms.wearable.DataClient.OnDataChangedListener { buffer ->
                buffer.use { events ->
                    for (event in events) {
                        val item = event.dataItem
                        if (item.uri.path == WearPaths.CURRENT_SUMMARY) {
                            val dataMapItem = com.google.android.gms.wearable.DataMapItem.fromDataItem(item)
                            val json = dataMapItem.dataMap.getString("summary_json")
                            trySend(
                                json?.let {
                                   Json.decodeFromString<WearPeriodSummary>(it)
                                }
                            )
                        }
                    }
                }
            }

            dataClient.addListener(listener)
            awaitClose { dataClient.removeListener(listener) }
        }

    suspend fun requestPeriodHistory(periodId: Long): List<WearExpenseItem> {
        val capability = capabilityClient.getCapability(
            "phone_app",
            com.google.android.gms.wearable.CapabilityClient.FILTER_REACHABLE
        ).await()

        val node = capability.nodes.firstOrNull() ?: return emptyList()

        val bytes = messageClient.sendRequest(
            node.id,
            WearPaths.REQUEST_PERIOD_HISTORY,
            periodId.toString().toByteArray()
        ).await()

        return kotlinx.serialization.json.Json.decodeFromString(String(bytes))
    }

    suspend fun addExpense(request: AddExpenseRequest) {
        val capability = capabilityClient.getCapability(
            "phone_app",
            com.google.android.gms.wearable.CapabilityClient.FILTER_REACHABLE
        ).await()

        val node = capability.nodes.firstOrNull() ?: return

        val json = Json.encodeToString(request)

        messageClient.sendMessage(
            node.id,
            WearPaths.ADD_EXPENSE,
            json.toByteArray()
        ).await()
    }
}