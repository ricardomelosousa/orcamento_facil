package com.orcamento.orcamentofacil.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.orcamento.orcamentofacil.notifications.BudgetNotifier
import com.orcamento.orcamentofacil.notifications.ExpenseUiEvent
import com.orcamento.orcamentofacil.ui.viewmodel.ExpenseFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseFormScreen(viewModel: ExpenseFormViewModel, onBack: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val templates by viewModel.templates.collectAsStateWithLifecycle()
    val types by viewModel.availableTypes.collectAsStateWithLifecycle()
    var expandedTemplate by remember { mutableStateOf(false) }
    var expandedType by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val brl = remember { java.text.NumberFormat.getCurrencyInstance(java.util.Locale("pt", "BR")) }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is ExpenseUiEvent.ExpenseSaved -> {
                    BudgetNotifier.showNotification(
                        context = context,
                        id = 1001,
                        title = "Gasto lançado com sucesso",
                        message = "${brl.format(templates[0].template.totalLimit)} Restante : ${brl.format(event.remaining)}"
                    )
                }
            }
        }
    }
    Scaffold(topBar = {
        TopAppBar(title = { Text("Lançar gasto") }, navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Voltar")
            }
        })
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = expandedTemplate,
                onExpandedChange = { expandedTemplate = it }) {
                OutlinedTextField(
                    value = templates.firstOrNull { it.template.id == state.selectedTemplateId }?.template?.name.orEmpty(),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Período base") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTemplate) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedTemplate,
                    onDismissRequest = { expandedTemplate = false }) {
                    templates.forEach {
                        DropdownMenuItem(
                            text = { Text(it.template.name) },
                            onClick = {
                                viewModel.selectTemplate(it.template.id)
                                expandedTemplate = false
                            }
                        )
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = expandedType,
                onExpandedChange = { expandedType = it }) {
                OutlinedTextField(
                    value = types.firstOrNull { it.id == state.selectedTypeId }?.name.orEmpty(),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de gasto") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedType) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedType,
                    onDismissRequest = { expandedType = false }) {
                    types.forEach {
                        DropdownMenuItem(
                            text = { Text(it.name) },
                            onClick = {
                                viewModel.selectType(it.id)
                                expandedType = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                state.amount,
                viewModel::updateAmount,
                label = { Text("Valor") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                state.expenseDate,
                viewModel::updateDate,
                label = { Text("Data (yyyy-MM-dd)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                state.description,
                viewModel::updateDescription,
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )
            state.error?.let { Text(it) }
            Button(
                onClick = { viewModel.save(onSuccess = onBack) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar lançamento")
            }
        }
    }
}
