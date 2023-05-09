package com.android.task.budgetmanagement.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.task.budgetmanagement.data.database.entity.AccountEntity
import com.android.task.budgetmanagement.utils.Constants.ACCOUNT_TABLE

/**
 * AccountDao contains the methods used for accessing the database, including queries.
 */
@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // <- Annotate the 'insertAccount' function below. Set the onConflict strategy to IGNORE so if exactly the same user exists, it will just ignore it.
    suspend fun insertAccount(noteEntity: AccountEntity)

    @Update
    suspend fun updateAccount(noteEntity: AccountEntity)

    @Delete
    suspend fun deleteAccount(noteEntity: AccountEntity)

    @Query("DELETE FROM ACCOUNT_TABLE")
    suspend fun deleteAllAccount()

    /**
     * Add a query to fetch all users (in account_table) in ascending order by their IDs.
     */
    @Query("SELECT * FROM $ACCOUNT_TABLE ORDER BY id ASC")
    fun getAllAccount(): LiveData<List<AccountEntity>>// <- This means function return type is List. Specifically, a List of accounts.

    @Query("SELECT * FROM $ACCOUNT_TABLE WHERE id LIKE :id")
    fun getAccount(id: Int): AccountEntity
}