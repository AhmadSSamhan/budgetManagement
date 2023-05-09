package com.android.task.budgetmanagement.data.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.android.task.budgetmanagement.utils.Constants.MONTH_YEAR_TABLE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = MONTH_YEAR_TABLE,
    foreignKeys = [ForeignKey(
        entity = AccountEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("accountId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class MonthYearEntity(
    @PrimaryKey(autoGenerate = true) val monthId: Long = 0L,
    val accountId: Long,
    var month: String = "",
    var year: Int = 0,
    var day: Int = 0,
    var incomeDinar: Double = 0.0,
    var incomeDollar: Double = 0.0,
    var spendingDinar: Double = 0.0,
    var spendingDollar: Double = 0.0,
    var currency: String=""
) : Parcelable{
    @Transient
    var transactions: List<TransactionEntity> = emptyList()
        get() = field
        set(value) {
            field = value
        }
}