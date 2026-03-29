package com.orcamento.orcamentofacilwatch.presentation.ui.expense

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import com.orcamento.orcamentofacilwatch.presentation.domain.WearExpenseType
import com.orcamento.orcamentofacilwatch.presentation.ui.ViewModel.WearAddExpenseViewModel
import androidx.wear.compose.material3.Text

@Composable
fun WearAddExpenseScreen(
    onBack: () -> Unit,
    viewModel: WearAddExpenseViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ScalingLazyColumn {
        item {
            Text("Novo gasto")
        }

        item {
            androidx.wear.compose.material3.Button(onClick = { viewModel.loadTypes() }) {
                Text("Carregar tipos")
            }
        }

        items(state.types.size) { index ->
            val type = state.types[index]
            androidx.wear.compose.material3.Button(onClick = {
                viewModel.selectType(type.id)
            }) {
                Text(type.name)
            }
        }

        item {
            androidx.wear.compose.material3.Button(onClick = { viewModel.saveExampleExpense() }) {
                Text("Salvar exemplo")
            }
        }

        item {
            androidx.wear.compose.material3.CompactButton(onClick = onBack) {
                Text("Voltar")
            }
        }
    }
}

data class WearAddExpenseUiState(
    val types: List<WearExpenseType> = emptyList(),
    val selectedTypeId: Long? = null
)