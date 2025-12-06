package com.group3.smartaccount.data.repository
import com.group3.smartaccount.data.local.TransactionDao
import com.group3.smartaccount.data.local.TransactionEntity
import com.group3.smartaccount.data.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepository(
    private val dao: TransactionDao
) {

    fun getAllTransactions(): Flow<List<Transaction>> =
        dao.getAllTransactions().map { list ->
            list.map { it.toDomain() }
        }

    suspend fun addTransaction(transaction: Transaction) {
        dao.insert(transaction.toEntity())
    }

    suspend fun updateTransaction(transaction: Transaction) {
        dao.update(transaction.toEntity())
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        transaction.id?.let {
            dao.delete(transaction.toEntity())
        }
    }

    private fun TransactionEntity.toDomain(): Transaction =
        Transaction(
            id = id,
            amount = amount,
            type = type,
            category = category,
            timestamp = timestamp,
            notes = notes,
            currency = currency
        )

    private fun Transaction.toEntity(): TransactionEntity =
        TransactionEntity(
            id = id ?: 0,
            amount = amount,
            type = type,
            category = category,
            timestamp = timestamp,
            notes = notes,
            currency = currency
        )
}
