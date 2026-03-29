package com.orcamento.orcamentofacilwatch.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.orcamento.orcamentofacilwatch.presentation.ui.ViewModel.WearAddExpenseViewModel
import com.orcamento.orcamentofacilwatch.presentation.ui.ViewModel.WearHistoryViewModel
import com.orcamento.orcamentofacilwatch.presentation.ui.ViewModel.WearHomeViewModel
import com.orcamento.orcamentofacilwatch.presentation.ui.ViewModel.WearViewModelFactory
import com.orcamento.orcamentofacilwatch.presentation.ui.expense.WearAddExpenseScreen
import com.orcamento.orcamentofacilwatch.presentation.ui.history.WearHistoryScreen
import com.orcamento.orcamentofacilwatch.presentation.ui.home.WearHomeScreen


@Composable
fun WearAppNavHost(
    viewModelFactory: ViewModelProvider.Factory,
    notificationPeriodId: Long = -1L,
    openHistory: Boolean = false
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = WearRoutes.Home.route
    ) {
        composable(WearRoutes.Home.route) {
            val vm: WearHomeViewModel = viewModel(factory = viewModelFactory)
            WearHomeScreen(
                viewModel = vm,
                onAddExpense = {
                    navController.navigate(WearRoutes.AddExpense.route)
                },
                onOpenHistory = { periodId ->
                    navController.navigate(WearRoutes.History.create(periodId))
                }
            )
        }

        composable(WearRoutes.AddExpense.route) {
            val vm: WearAddExpenseViewModel = viewModel(factory = viewModelFactory)

            WearAddExpenseScreen(
                viewModel = vm,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = WearRoutes.History.route,
            arguments = listOf(
                navArgument("periodId") { type = NavType.LongType }
            )
        ) { backStackEntry ->

            val periodId = backStackEntry.arguments?.getLong("periodId") ?: -1L

            val vm: WearHistoryViewModel = viewModel(factory = viewModelFactory)

            WearHistoryScreen(
                viewModel = vm,
                periodId = periodId,
                onBack = { navController.popBackStack() }
            )
        }

//        composable(WearRoutes.History.route) {
//            val vm: WearHistoryViewModel = viewModel(factory = viewModelFactory)
//            WearHistoryScreen(
//                1L,
//                viewModel = vm,
//                onBack = {navController.popBackStack()}
//            )
//        }
    }
}


//    val navController = rememberNavController()
//
//    androidx.compose.runtime.LaunchedEffect(openHistory, notificationPeriodId) {
//        if (openHistory && notificationPeriodId > 0) {
//            navController.navigate(WearRoutes.History.create(notificationPeriodId))
//        }
//    }
//
//    androidx.navigation.compose.NavHost(
//        navController = navController,
//        startDestination = WearRoutes.Home.route
//    ) {
//        composable(WearRoutes.Home.route) {
//            WearHomeScreen(
//                //onAddExpense = { navController.navigate(WearRoutes.AddExpense.route) },
//                //onOpenHistory = { periodId -> navController.navigate(WearRoutes.History.create(periodId)) }
//            )
//        }
//
//        composable(WearRoutes.AddExpense.route) {
//            WearAddExpenseScreen(
//                onBack = { navController.popBackStack() }
//            )
//        }
//
//        composable(
//            route = WearRoutes.History.route,
//            arguments = listOf(
//                androidx.navigation.navArgument("periodId") {
//                    type = androidx.navigation.NavType.LongType
//                }
//            )
//        ) { backStackEntry ->
//            val periodId = backStackEntry.arguments?.getLong("periodId") ?: -1L
//            WearHistoryScreen(
//                periodId = periodId,
//                onBack = { navController.popBackStack() }
//            )
//        }
//    }
//}