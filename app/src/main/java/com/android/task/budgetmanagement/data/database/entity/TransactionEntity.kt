package com.android.task.budgetmanagement.data.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.task.budgetmanagement.utils.Constants.TRANSACTION_TABLE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TRANSACTION_TABLE)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val transactionId: Long = 0L,
    val month: String,
    val year: String,
    val date: String,
    var name: String,
    var total: Double,
    var isIncome: Boolean,
    val monthId: Long,
    var currency: String
) : Parcelable