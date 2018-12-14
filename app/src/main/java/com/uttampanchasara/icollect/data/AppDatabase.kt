package com.uttampanchasara.icollect.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.uttampanchasara.icollect.data.repository.msg.ChatMessages
import com.uttampanchasara.icollect.data.repository.msg.ChatMessagesDao
import com.uttampanchasara.icollect.data.repository.record.RecordData
import com.uttampanchasara.icollect.data.repository.record.RecordDataDao
import com.uttampanchasara.icollect.data.repository.user.User
import com.uttampanchasara.icollect.data.repository.user.UserDao

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/15/2018
 */
@Database(entities = [(User::class), (RecordData::class), (ChatMessages::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun recordDataDao(): RecordDataDao

    abstract fun chatMessageDao(): ChatMessagesDao
}