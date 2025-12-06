package com.group3.smartaccount.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.group3.smartaccount.data.model.Transaction
import com.group3.smartaccount.data.model.TransactionType

@OptIn(ExperimentalMaterial3Api::class)   // 👈 fixes the experimental API error
@Composable
fun AddEditTransactionScreen(
    existing: Transaction?,
    onSave: (Transaction) -> Unit,
    onCancel: () -> Unit
) {
    var amountText by remember { mutableStateOf(existing?.amount?.toString() ?: "") }
    var category by remember { mutableStateOf(existing?.category ?: "") }
    var notes by remember { mutableStateOf(existing?.notes ?: "") }
    var type by remember { mutableStateOf(existing?.type ?: TransactionType.EXPENSE) }

    var amountError by remember { mutableStateOf<String?>(null) }
    var categoryError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (existing == null) "Add Transaction" else "Edit Transaction")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = amountText,
                onValueChange = {
                    amountText = it
                    amountError = null
                },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                isError = amountError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (amountError != null) {
                Text(amountError!!, color = MaterialTheme.colorScheme.error)
            }


            OutlinedTextField(
                value = category,
                onValueChange = {
                    category = it
                    categoryError = null
                },
                label = { Text("Category") },
                isError = categoryError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (categoryError != null) {
                Text(categoryError!!, color = MaterialTheme.colorScheme.error)
            }


            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes") },
                modifier = Modifier.fillMaxWidth()
            )


            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FilterChip(
                    selected = type == TransactionType.INCOME,
                    onClick = { type = TransactionType.INCOME },
                    label = { Text("Income") }
                )
                FilterChip(
                    selected = type == TransactionType.EXPENSE,
                    onClick = { type = TransactionType.EXPENSE },
                    label = { Text("Expense") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(onClick = onCancel) {
                    Text("Cancel")
                }

                Button(
                    onClick = {

                        val amount = amountText.toDoubleOrNull()
                        if (amount == null || amount <= 0) {
                            amountError = "Enter a valid positive amount."
                            return@Button
                        }
                        if (category.isBlank()) {
                            categoryError = "Category is required."
                            return@Button
                        }

                        val tx = if (existing == null) {
                            Transaction(
                                id = 0,
                                amount = amount,
                                type = type,
                                category = category,
                                notes = notes,
                                timestamp = System.currentTimeMillis()
                            )
                        } else {
                            existing.copy(
                                amount = amount,
                                type = type,
                                category = category,
                                notes = notes
                            )
                        }

                        onSave(tx)
                    }
                ) {
                    Text("Save")
                }
            }
        }
    }
}
