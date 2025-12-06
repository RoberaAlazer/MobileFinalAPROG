package com.group3.smartaccount.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.group3.smartaccount.data.model.TransactionType

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val type: TransactionType,
    val category: String,
    val timestamp: Long,
    val notes: String?,
    val currency: String
)
