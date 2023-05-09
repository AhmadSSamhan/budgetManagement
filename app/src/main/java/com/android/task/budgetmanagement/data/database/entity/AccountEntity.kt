package com.android.task.budgetmanagement.data.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.task.budgetmanagement.utils.Constants.ACCOUNT_TABLE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = ACCOUNT_TABLE)// Account Entity represents a table within the database.
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    val accountName: String="",
    var dinarTotal: Double=0.0,
    var dollarTotal: Double=0.0
) : Parcelable