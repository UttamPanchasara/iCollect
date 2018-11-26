package com.uttampanchasara.baseprojectkotlin.data.repository.user

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/15/2018
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM user")
    fun getUser(): List<User>

    @Query("SELECT * FROM user")
    fun getLiveUser(): LiveData<List<User>>

    @Query("SELECT count(*) FROM user")
    fun getTotalUsers(): Int
}