package com.uttampanchasara.icollect.data.repository.msg

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * @since 12/14/2018
 */
@Entity(tableName = "ChatMessages")
data class ChatMessages(val roomId: String,
                        val userName: String,
                        val userId: String,
                        val message: String,
                        var messageType: Int, // USER_TYPE = > enum
                        @PrimaryKey
                        val time: Long) {
    companion object {
        val ROOM_ID = "roomId"
        val USER_NAME = "userName"
        val USER_ID = "userId"
        val MESSAGE = "message"
        val MESSAGE_TYPE = "messageType"
        val TIME = "time"
    }

    enum class TYPE(val value: Int) {
        SENT(0),
        RECEIVE(1)
    }
}