package com.orcamento.orcamentofacilwatch.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.orcamento.orcamentofacilwatch.presentation.ui.ViewModel.WearHomeViewModel
import com.orcamento.orcamentofacilwatch.presentation.ui.expense.WearAddExpenseScreen
import com.orcamento.orcamentofacilwatch.presentation.ui.history.WearHistoryScreen
import com.orcamento.orcamentofacilwatch.presentation.ui.home.WearHomeScreen


@Composable
fun WearAppNavHost(
    wearHomeViewModel: WearHomeViewModel,
    notificationPeriodId: Long = -1L,
    openHistory: Boolean = false
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = WearRoutes.Home.route
    ) {
        composable(WearRoutes.Home.route) {
            WearHomeScreen(
                viewModel = wearHomeViewModel,
                onAddExpense = {
                    navController.navigate(WearRoutes.AddExpense.route)
                },
                onOpenHistory = { periodId ->
                    navController.navigate(WearRoutes.History.create(periodId))
                }
            )
        }
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