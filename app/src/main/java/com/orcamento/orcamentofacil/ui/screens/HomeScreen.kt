package com.orcamento.orcamentofacil.ui.screens
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
import com.orcamento.orcamentofacil.ui.viewmodel.HomeViewModel
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
private val brl = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onAddTemplate: () -> Unit,
    onEditTemplate: (Long) -> Unit,
    onAddExpense: () -> Unit,
    onOpenPeriod: (Long) -> Unit
) {
    val periods by viewModel.periods.collectAsStateWithLifecycle()
    val templates by viewModel.templates.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Orçamento Fácil") }) },
//        bottomBar = {
//            BottomAppBar(modifier = Modifier.wrapContentHeight()) {
//                val currentPeriod = periods
//                    .filter { it.spent > 0 }
//                    .maxByOrNull { it.startDate } // pega o mais recente com gasto
//
//                currentPeriod?.let { period ->
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp)
//                    ) {
//                        Row() {
//                            Column() {
//                                Text("Limite: ${brl.format(period.totalLimit)}")
//                                Text("Gasto: ${brl.format(period.spent)}")
//                                Text("Restante: ${brl.format(period.totalLimit - period.spent)}")
//                            }
//                            Column() {
//                                Text("Limite: ${brl.format(period.totalLimit)}")
//                                Text("Gasto: ${brl.format(period.spent)}")
//                                Text("Restante: ${brl.format(period.totalLimit - period.spent)}")
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        bottomBar = {
//            val currentPeriod = periods
//                   .filter { it.spent > 0 }
//                  .maxByOrNull { it.startDate }
//            currentPeriod?.let { period ->
//                val restante = period.totalLimit - period.spent
//
//                Surface(
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp)
//                    ) {
//                        Text("Limite: ${brl.format(period.totalLimit)}")
//                        Text("Gasto: ${brl.format(period.spent)}")
//                        Text("Restante bruto: $restante")
//                        Text("Restante formatado: ${brl.format(restante)}")
//                    }
//                }
//            }
//        }

        bottomBar = {
            val periodsWithSpent = periods.filter { it.spent > 0 }

            if (periodsWithSpent.isNotEmpty()) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        contentPadding = PaddingValues(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(periodsWithSpent) { period ->
                            val restante = period.totalLimit - period.spent

                            Card(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                androidx.compose.foundation.layout.Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    Text(
                                        text = "Período: ${period.name}",
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                    Text(
                                        text = "${period.startDate} até ${period.endDate}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text("Limite: ${brl.format(period.totalLimit)}")
                                    Text("Gasto: ${brl.format(period.spent)}")
                                    Text("Restante: ${brl.format(restante)}")
                                }
                            }
                        }
                    }
                }
            }
        }
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
                    Button(onClick = onAddTemplate, modifier = Modifier.fillMaxWidth()) {
                        Text("Cadastrar período/tipos")
                    }
                    Button(onClick = onAddExpense, modifier = Modifier.fillMaxWidth()) {
                        Text("Lançar gasto")
                    }
                    templates.forEach {
                        Text(
                            text = "Editar: ${it.template.name} (${it.types.joinToString { type -> type.name }})",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onEditTemplate(it.template.id) }
                                .padding(vertical = 4.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            items(periods) { period ->
                PeriodCard(summary = period, onClick = { onOpenPeriod(period.periodId) })
            }
        }
    }
}


//@Preview(showBackground = true)
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TopBarComMenu() {
//    // Estado para controlar se o menu está expandido
//    var menuExibido by remember { mutableStateOf(false) }
//
//    TopAppBar(
//        title = { Text("Exemplo Menu") },
//        actions = {
//            // Botão de menu (três pontos)
//            IconButton(onClick = { menuExibido = true }) {
//                Icon(Icons.Default.MoreVert, contentDescription = "Menu")
//            }
//            // DropdownMenu posicionado no ícone
//            DropdownMenu(
//                expanded = menuExibido,
//                onDismissRequest = { menuExibido = false } // Fecha ao tocar fora
//            ) {
//                DropdownMenuItem(
//                    text = { Text("Opção 1") },
//                    onClick = {
//                        menuExibido = false
//                        // Ação da Opção 1
//                    }
//                )
//                DropdownMenuItem(
//                    text = { Text("Opção 2") },
//                    onClick = {
//                        menuExibido = false
//                        // Ação da Opção 2
//                    }
//                )
//            }
//        }
//    )
//}