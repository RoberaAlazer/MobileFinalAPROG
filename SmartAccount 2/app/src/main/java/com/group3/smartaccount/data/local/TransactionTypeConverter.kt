package com.group3.smartaccount.data.local
import androidx.room.TypeConverter
import com.group3.smartaccount.data.model.TransactionType

class TransactionTypeConverter {

    @TypeConverter
    fun fromType(type: TransactionType): String = type.name

    @TypeConverter
    fun toType(value: String): TransactionType = TransactionType.valueOf(value)
}
