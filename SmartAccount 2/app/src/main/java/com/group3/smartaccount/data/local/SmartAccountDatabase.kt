package com.group3.smartaccount.data.local
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [TransactionEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TransactionTypeConverter::class)
abstract class SmartAccountDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}
