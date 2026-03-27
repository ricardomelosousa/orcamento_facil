package com.orcamento.orcamentofacilwatch.presentation.ui.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import com.orcamento.orcamentofacilwatch.presentation.ui.ViewModel.WearHistoryViewModel


@Composable
fun WearHistoryScreen(
    periodId: Long,
    onBack: () -> Unit,
    viewModel: WearHistoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val items by viewModel.items.collectAsStateWithLifecycle()
    val brl = remember { java.text.NumberFormat.getCurrencyInstance(java.util.Locale("pt", "BR")) }

    androidx.compose.runtime.LaunchedEffect(periodId) {
        if (periodId > 0) viewModel.load(periodId)
    }

    TransformingLazyColumn {
        item {
            androidx.wear.compose.material3.Button(onClick = onBack) {
                androidx.wear.compose.material3.Text("Voltar")
            }
        }

        items(items.size) { index ->
            val item = items[index]
            androidx.wear.compose.material3.Text("${item.typeName}: ${brl.format(item.amount)}")
            if (item.description.isNotBlank()) {
                androidx.wear.compose.material3.Text(item.description)
            }
            androidx.wear.compose.material3.Text(item.expenseDate)
        }
    }
}


