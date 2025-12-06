package com.group3.smartaccount.screens
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.group3.smartaccount.ui.theme.Green
import com.group3.smartaccount.ui.theme.Red
import com.group3.smartaccount.viewmodel.TransactionUiState

@Composable
fun ReportsScreen(
    uiState: TransactionUiState,
    cadToUsd: Double?,
    cadToEur: Double?,
    cadToGbp: Double?
) {
    val total = uiState.totalIncome + uiState.totalExpense
    val incomeShare = if (total > 0) uiState.totalIncome / total else 0.0
    val expenseShare = if (total > 0) uiState.totalExpense / total else 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Reports", style = MaterialTheme.typography.headlineMedium)

        // Overview card
        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Overview", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Text("Total income: ${uiState.totalIncome}")
                Text("Total expense: ${uiState.totalExpense}")
                Text("Balance (CAD): ${uiState.balance}")
            }
        }

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Income vs Expense", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(12.dp))

                IncomeExpenseGraph(uiState.totalIncome, uiState.totalExpense)

                Spacer(Modifier.height(12.dp))
                Text("Income share: ${(incomeShare * 100).toInt()}%")
                LinearProgressIndicator(
                    progress = incomeShare.toFloat(),
                    modifier = Modifier.height(8.dp).fillMaxWidth(),
                    color = Green
                )

                Spacer(Modifier.height(12.dp))
                Text("Expense share: ${(expenseShare * 100).toInt()}%")
                LinearProgressIndicator(
                    progress = expenseShare.toFloat(),
                    modifier = Modifier.height(8.dp).fillMaxWidth(),
                    color = Red
                )
            }
        }

        // Currency Info card
        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Currency Info", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))

                if (cadToUsd != null || cadToEur != null || cadToGbp != null) {

                    cadToUsd?.let {
                        Text("1 CAD ≈ ${"%.4f".format(it)} USD")
                        Text("Balance in USD: ${"%.2f".format(uiState.balance * it)}")
                        Spacer(Modifier.height(8.dp))
                    }

                    cadToEur?.let {
                        Text("1 CAD ≈ ${"%.4f".format(it)} EUR")
                        Text("Balance in EUR: ${"%.2f".format(uiState.balance * it)}")
                        Spacer(Modifier.height(8.dp))
                    }

                    cadToGbp?.let {
                        Text("1 CAD ≈ ${"%.4f".format(it)} GBP")
                        Text("Balance in GBP: ${"%.2f".format(uiState.balance * it)}")
                    }

                } else {
                    Text("Unable to load exchange rates.")
                }
            }
        }
    }
}

@Composable
fun IncomeExpenseGraph(income: Double, expense: Double) {
    val max = maxOf(income, expense, 1.0)

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(horizontal = 32.dp)
    ) {
        val barW = size.width / 4

        val inH = (income / max) * size.height
        val exH = (expense / max) * size.height

        drawRect(
            color = Green,
            topLeft = Offset(barW, size.height - inH.toFloat()),
            size = androidx.compose.ui.geometry.Size(barW, inH.toFloat())
        )

        drawRect(
            color = Red,
            topLeft = Offset(barW * 2.5f, size.height - exH.toFloat()),
            size = androidx.compose.ui.geometry.Size(barW, exH.toFloat())
        )
    }
}
