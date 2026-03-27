package com.orcamento.orcamentofacilwatch.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.CompactButton
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import com.orcamento.orcamentofacilwatch.presentation.domain.WearPeriodSummary
import com.orcamento.orcamentofacilwatch.presentation.ui.ViewModel.WearHomeViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun WearHomeScreen(
    onAddExpense: () -> Unit,
    onOpenHistory: (Long) -> Unit,
    viewModel: WearHomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val brl = remember { NumberFormat.getCurrencyInstance(Locale("pt", "BR")) }

    TransformingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
        contentPadding = PaddingValues(top = 20.dp, bottom = 20.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            Text(
                "Orçamento Fácil", style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
            )
        }

        item {
            when (val state = uiState) {
                is WearHomeUiState.Loading -> Text("Carregando...")
                is WearHomeUiState.Empty -> Text("Sem período atual")
                is WearHomeUiState.Success -> {
                    Text(state.summary.label)
                    Text("Limite: ${brl.format(state.summary.totalLimit)}")
                    Text("Gasto: ${brl.format(state.summary.spent)}")
                    Text(
                        "Restante: ${brl.format(state.summary.remaining)}",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                    )
                }
            }
        }

        item {
            Button(onClick = onAddExpense) {
                Text("Lançar gasto")
            }
        }

        item {
            val currentPeriodId = (uiState as? WearHomeUiState.Success)?.summary?.periodId ?: -1L
            CompactButton(
                onClick = { if (currentPeriodId > 0) onOpenHistory(currentPeriodId) }
            ) {
                Text("Histórico")
            }
        }
    }
}


sealed interface WearHomeUiState {
    data object Loading : WearHomeUiState
    data object Empty : WearHomeUiState
    data class Success(val summary: WearPeriodSummary) : WearHomeUiState
}