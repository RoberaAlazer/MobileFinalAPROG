package com.group3.smartaccount.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.group3.smartaccount.viewmodel.TransactionUiState
import com.group3.smartaccount.ui.theme.Green
import com.group3.smartaccount.ui.theme.Red
@Composable
fun HomeScreen(uiState: TransactionUiState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.headlineMedium
        )

        SummaryCard(
            title = "Current Balance",
            amount = uiState.balance,
            amountColor = if (uiState.balance >= 0) Green else Red
        )

        SummaryCard(
            title = "Total Income",
            amount = uiState.totalIncome,
            amountColor = Green
        )

        SummaryCard(
            title = "Total Expenses",
            amount = uiState.totalExpense,
            amountColor = Red
        )
    }
}

@Composable
private fun SummaryCard(title: String, amount: Double, amountColor: androidx.compose.ui.graphics.Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))
            Text(
                text = "$${"%.2f".format(amount)}",
                style = MaterialTheme.typography.headlineMedium.copy(color = amountColor)
            )
        }
    }
}
