package com.group3.smartaccount.data.model
data class Transaction(
    val id: Int? = null,
    val amount: Double,
    val type: TransactionType,
    val category: String,
    val timestamp: Long,
    val notes: String? = null,
    val currency: String = "CAD"
)
enum class TransactionType {
    INCOME,
    EXPENSE
}
