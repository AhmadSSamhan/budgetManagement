package com.android.task.budgetmanagement.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TransactionWithSets(
    @Embedded val monthYearEntity: MonthYearEntity,
    @Relation(
        parentColumn = "monthId",
        entityColumn = "transactionId"
    )
    val sets: List<TransactionEntity>
)
