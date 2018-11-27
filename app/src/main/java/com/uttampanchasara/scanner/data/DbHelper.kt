package com.uttampanchasara.scanner.data

import android.arch.lifecycle.LiveData
import com.uttampanchasara.scanner.data.repository.record.RecordData
import io.reactivex.Observable

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/15/2018
 */
interface DbHelper {
    fun insertRecord(recordData: RecordData): Observable<Boolean>

    fun getRecords(): LiveData<List<RecordData>>

    fun searchRecord(query: String?, date: String?): Observable<List<RecordData>>

    fun getRecordsInGroup(): LiveData<List<RecordData>>

    fun getAllDates(): LiveData<List<String>>

    fun getRecordsFromDate(date: String?): LiveData<List<RecordData>>
}