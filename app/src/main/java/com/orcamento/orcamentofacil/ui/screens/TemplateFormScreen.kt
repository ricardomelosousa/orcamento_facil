package com.orcamento.orcamentofacil.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.orcamento.orcamentofacil.ui.viewmodel.TemplateFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateFormScreen(viewModel: TemplateFormViewModel, onBack: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(topBar = { TopAppBar(title = { Text("Novo período") }) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(state.name, viewModel::updateName, label = { Text("Nome do período") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(state.startDay, viewModel::updateStartDay, label = { Text("Dia inicial") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(state.endDay, viewModel::updateEndDay, label = { Text("Dia final") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(state.totalLimit, viewModel::updateLimit, label = { Text("Limite total") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(
                state.typesText,
                viewModel::updateTypesFromText,
                label = { Text("Tipos de gasto (1 por linha)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4
            )
            androidx.compose.foundation.layout.Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Renovar automaticamente")
                Switch(checked = state.autoRenew, onCheckedChange = viewModel::updateAutoRenew)
            }
            state.error?.let { Text(it) }
            Button(
                onClick = { viewModel.save(onSuccess = onBack) },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Salvar") }
        }
    }
}
