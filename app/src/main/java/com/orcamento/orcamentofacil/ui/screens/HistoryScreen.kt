package com.orcamento.orcamentofacil.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.orcamento.orcamentofacil.ui.components.PeriodCard
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import com.orcamento.orcamentofacil.ui.viewmodel.HistoryViewModel

private val brl = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    onAddTemplate: () -> Unit,
    onEditTemplate: (Long) -> Unit,
    onAddExpense: () -> Unit,
    onOpenPeriod: (Long) -> Unit,
    onBack: () -> Unit
) {
    val periods by viewModel.periods.collectAsStateWithLifecycle()
    val templates by viewModel.templates.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Orçamento Fácil") }, navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Voltar")
                }
            })
        },

    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Modelos cadastrados: ${templates.size}",
                        style = MaterialTheme.typography.titleMedium
                    )
//                        Button(onClick = onAddTemplate, modifier = Modifier.fillMaxWidth()) {
//                            Text("Cadastrar período/tipos")
//                        }
//                        Button(onClick = onAddExpense, modifier = Modifier.fillMaxWidth()) {
//                            Text("Lançar gasto")
//                        }
//                        templates.forEach {
//                            Text(
//                                text = "Editar: ${it.template.name} (${it.types.joinToString { type -> type.name }})",
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .clickable { onEditTemplate(it.template.id) }
//                                    .padding(vertical = 4.dp),
//                                style = MaterialTheme.typography.bodyMedium
//                            )
//                        }
                }
            }
            items(periods) { period ->
                PeriodCard(summary = period, onClick = { onOpenPeriod(period.periodId) })
            }
        }
    }
}


