package com.orcamento.orcamentofacil.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orcamento.orcamentofacil.domain.PeriodSummary
import java.text.NumberFormat
import java.util.Locale

private val brl = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

@Composable
fun PeriodCard(summary: PeriodSummary, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(summary.name, style = MaterialTheme.typography.titleMedium)
            Text(summary.label, style = MaterialTheme.typography.bodyMedium)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Limite: ${brl.format(summary.totalLimit)}")
                Text("Gasto: ${brl.format(summary.spent)}")
            }
            AssistChip(onClick = {}, label = { Text("Restante: ${brl.format(summary.remaining)}") })
        }
    }
}
