package com.orcamento.orcamentofacil.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.orcamento.core_model.AppContainer
import com.orcamento.orcamentofacil.ui.screens.ExpenseFormScreen
import com.orcamento.orcamentofacil.ui.screens.HistoryScreen
import com.orcamento.orcamentofacil.ui.screens.HomeScreen
import com.orcamento.orcamentofacil.ui.screens.PeriodDetailScreen
import com.orcamento.orcamentofacil.ui.screens.TemplateFormScreen
import com.orcamento.orcamentofacil.ui.viewmodel.AppViewModelFactory
import com.orcamento.orcamentofacil.ui.viewmodel.ExpenseFormViewModel
import com.orcamento.orcamentofacil.ui.viewmodel.HistoryViewModel
import com.orcamento.orcamentofacil.ui.viewmodel.HomeViewModel
import com.orcamento.orcamentofacil.ui.viewmodel.PeriodDetailViewModel
import com.orcamento.orcamentofacil.ui.viewmodel.TemplateFormViewModel

object Routes {
    const val HOME = "home"
    const val TEMPLATE_FORM = "template_form"
    const val EXPENSE_FORM = "expense_form"
    const val PERIOD_DETAIL = "period_detail"

    const val HISTORY_FORM = "history_form"

}

@Composable
fun AppNavHost(appContainer: AppContainer, openHistory: Boolean, notificationPeriodId: Long) {
    val navController = rememberNavController()

    LaunchedEffect(openHistory, notificationPeriodId) {
        if (openHistory && notificationPeriodId > 0) {
            navController.navigate(Routes.HISTORY_FORM){
                launchSingleTop = true
            }
        }
    }


    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            val vm: HomeViewModel = viewModel(factory = AppViewModelFactory(appContainer.repository))
            HomeScreen(
                viewModel = vm,
                onAddTemplate = { navController.navigate(Routes.TEMPLATE_FORM) },
                onEditTemplate = { navController.navigate("${Routes.TEMPLATE_FORM}?templateId=$it") },
                onAddExpense = { navController.navigate(Routes.EXPENSE_FORM) },
                onOpenPeriod = { navController.navigate("${Routes.PERIOD_DETAIL}/$it") },
                onNavigateToPeriods = { navController.navigate(Routes.TEMPLATE_FORM)  },
                onNavigateToHistory = { navController.navigate(Routes.HISTORY_FORM) },
                onNavigateToExpanse = {navController.navigate(Routes.EXPENSE_FORM)},
                onNavigateToSettings = { navController.navigate(Routes.EXPENSE_FORM)  }
            )
        }
        composable(
            route = "${Routes.TEMPLATE_FORM}?templateId={templateId}",
            arguments = listOf(navArgument("templateId") { type = NavType.LongType; defaultValue = -1L })
        ) { backStackEntry ->
            val vm: TemplateFormViewModel = viewModel(factory = AppViewModelFactory(appContainer.repository))
            val templateId = backStackEntry.arguments?.getLong("templateId") ?: -1L
            LaunchedEffect(templateId) {
                if (templateId > 0) vm.loadTemplate(templateId)
            }
            TemplateFormScreen(viewModel = vm, onBack = { navController.popBackStack() })
        }
        composable(Routes.EXPENSE_FORM) {
            val vm: ExpenseFormViewModel = viewModel(factory = AppViewModelFactory(appContainer.repository))
            ExpenseFormScreen(viewModel = vm, onBack = { navController.popBackStack() })
        }
        composable(Routes.HISTORY_FORM) {
            val vm: HistoryViewModel = viewModel(factory = AppViewModelFactory(appContainer.repository))
            HistoryScreen( viewModel = vm,
                onAddTemplate = { navController.navigate(Routes.TEMPLATE_FORM) },
                onEditTemplate = { navController.navigate("${Routes.TEMPLATE_FORM}?templateId=$it") },
                onAddExpense = { navController.navigate(Routes.EXPENSE_FORM) },
                onOpenPeriod = { navController.navigate("${Routes.PERIOD_DETAIL}/$it") },
                onBack = { navController.popBackStack() }
            )
            //val vm: ExpenseFormViewModel = viewModel(factory = AppViewModelFactory(appContainer.repository))
            //ExpenseFormScreen(viewModel = vm, onBack = { navController.popBackStack() })
        }
        composable(
            route = "${Routes.PERIOD_DETAIL}/{periodId}",
            arguments = listOf(navArgument("periodId") { type = NavType.LongType })
        ) { backStackEntry ->
            val periodId = backStackEntry.arguments?.getLong("periodId") ?: 0L
            val vm: PeriodDetailViewModel = viewModel(factory = AppViewModelFactory(appContainer.repository, periodId))
            PeriodDetailScreen(viewModel = vm, onBack = { navController.popBackStack() })
        }
    }
}
