package com.uttampanchasara.baseprojectkotlin.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.uttampanchasara.baseprojectkotlin.data.repository.record.RecordData
import com.uttampanchasara.baseprojectkotlin.data.repository.record.RecordDataDao
import com.uttampanchasara.baseprojectkotlin.data.repository.user.User
import com.uttampanchasara.baseprojectkotlin.data.repository.user.UserDao

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/15/2018
 */
@Database(entities = [(User::class), (RecordData::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun recordDataDao(): RecordDataDao
}