package com.uttampanchasara.icollect.data

import android.arch.lifecycle.LiveData
import com.uttampanchasara.icollect.data.repository.record.RecordData
import com.uttampanchasara.icollect.data.repository.user.User
import io.reactivex.Observable

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/15/2018
 */
interface DbHelper {
    fun insertRecord(recordData: RecordData): Observable<Boolean>

    fun getRecords(): LiveData<List<RecordData>>

    fun getUsers(): LiveData<List<User>>

    fun getAllRecords(): Observable<List<RecordData>>

    fun searchRecord(query: String?, date: String?): Observable<List<RecordData>>

    fun getRecordsInGroup(): LiveData<List<RecordData>>

    fun getAllDates(): LiveData<List<String>>

    fun getLiveRecordsFromDate(date: String?): LiveData<List<RecordData>>

    fun getRecordsFromDate(date: String?): Observable<List<RecordData>>

    fun insertUsers(user: List<User>): Observable<Boolean>
}