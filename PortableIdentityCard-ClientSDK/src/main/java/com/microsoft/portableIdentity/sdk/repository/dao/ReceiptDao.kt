package com.microsoft.portableIdentity.sdk.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import com.microsoft.portableIdentity.sdk.cards.receipts.Receipt

@Dao
interface ReceiptDao {

    @Query("SELECT * FROM Receipt")
    fun getAllReceipts(): LiveData<List<Receipt>>

    @Query("SELECT cardId FROM Receipt")
    fun getAllReceiptsByCardId(cardId: String): LiveData<List<Receipt>>

    @Insert
    suspend fun insert(receipt: Receipt)
}