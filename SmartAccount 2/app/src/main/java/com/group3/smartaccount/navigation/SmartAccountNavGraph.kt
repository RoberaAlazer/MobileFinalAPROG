package com.group3.smartaccount.navigation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.group3.smartaccount.screens.AddEditTransactionScreen
import com.group3.smartaccount.screens.HomeScreen
import com.group3.smartaccount.screens.ReportsScreen
import com.group3.smartaccount.screens.SplashScreen
import com.group3.smartaccount.screens.TransactionListScreen
import com.group3.smartaccount.viewmodel.TransactionUiState
import com.group3.smartaccount.viewmodel.TransactionViewModel

@Composable
fun SmartAccountNavGraph(
    navController: NavHostController,
    viewModel: TransactionViewModel,
    uiState: TransactionUiState
) {
    val cadToUsd = viewModel.cadToUsd.collectAsState().value
    val cadToEur = viewModel.cadToEur.collectAsState().value
    val cadToGbp = viewModel.cadToGbp.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        composable("splash") {
            SplashScreen(navController)
        }


        composable("home") {
            HomeScreen(uiState)
        }

        composable("transactions") {
            TransactionListScreen(
                transactions = uiState.transactions,
                onAddClick = { navController.navigate("addEditTransaction") },
                onDeleteClick = { tx -> viewModel.deleteTransaction(tx) },
                onEditClick = { tx ->
                    navController.navigate("addEditTransaction?transactionId=${tx.id}")
                }
            )
        }
        composable(
            route = "addEditTransaction?transactionId={transactionId}",
            arguments = listOf(
                navArgument("transactionId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { entry ->
            val id = entry.arguments?.getInt("transactionId") ?: -1
            val existing = uiState.transactions.find { it.id == id }

            AddEditTransactionScreen(
                existing = existing,
                onSave = { tx ->
                    if (existing == null) {
                        viewModel.addTransaction(tx)
                    } else {
                        viewModel.updateTransaction(tx.copy(id = existing.id))
                    }
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }


        composable("reports") {
            ReportsScreen(
                uiState = uiState,
                cadToUsd = cadToUsd,
                cadToEur = cadToEur,
                cadToGbp = cadToGbp
            )
        }
    }
}
