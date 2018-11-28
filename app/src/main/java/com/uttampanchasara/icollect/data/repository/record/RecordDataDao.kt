package com.uttampanchasara.icollect.data.repository.record

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
interface RecordDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recordData: RecordData)

    @Query("SELECT * FROM records ORDER BY time DESC")
    fun getRecords(): LiveData<List<RecordData>>

    @Query("SELECT * FROM records ORDER BY time DESC")
    fun getAllRecords(): List<RecordData>

    @Query("SELECT count(*) FROM records")
    fun getTotalRecords(): Int

    @Query("SELECT * FROM records WHERE name LIKE :query AND date =:date ORDER BY time DESC")
    fun searchRecord(query: String?, date: String?): List<RecordData>

    @Query("SELECT * FROM records GROUP BY date ORDER BY time DESC")
    fun getAllRecordsInGroup(): LiveData<List<RecordData>>

    @Query("SELECT DISTINCT date FROM records ORDER BY time DESC")
    fun getAllDates(): LiveData<List<String>>

    @Query("SELECT * FROM records WHERE date =:date ORDER BY time DESC")
    fun getLiveRecordsFromDate(date: String?): LiveData<List<RecordData>>

    @Query("SELECT * FROM records WHERE date =:date ORDER BY time DESC")
    fun getRecordsFromDate(date: String?): List<RecordData>
}