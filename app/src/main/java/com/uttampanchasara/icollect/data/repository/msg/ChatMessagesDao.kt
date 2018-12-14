package com.uttampanchasara.icollect.data.repository.msg

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 * @since 12/14/2018
 */
@Dao
interface ChatMessagesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(chatMessage: ChatMessages)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(user: List<ChatMessages>)

    @Query("SELECT * FROM chatmessages")
    fun getMessages(): List<ChatMessages>

    @Query("SELECT * FROM chatmessages")
    fun getLiveMessages(): LiveData<List<ChatMessages>>

    @Query("SELECT * FROM chatmessages WHERE roomId =:roomId ORDER BY time DESC")
    fun getRoomMessages(roomId: String): LiveData<List<ChatMessages>>
}