package com.uttampanchasara.baseprojectkotlin.data

import android.arch.lifecycle.LiveData
import com.uttampanchasara.baseprojectkotlin.data.repository.record.RecordData
import io.reactivex.Observable

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/15/2018
 */
interface DbHelper {
    fun insertRecord(recordData: RecordData): Observable<Boolean>

    fun getRecords(): LiveData<List<RecordData>>
}