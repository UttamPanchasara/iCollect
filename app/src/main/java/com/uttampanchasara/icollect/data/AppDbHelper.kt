package com.uttampanchasara.icollect.data

import android.arch.lifecycle.LiveData
import com.uttampanchasara.icollect.data.repository.record.RecordData
import io.reactivex.Observable
import javax.inject.Inject

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/15/2018
 */
class AppDbHelper
@Inject internal constructor(private val appDatabase: AppDatabase) : DbHelper {

    override fun getRecordsFromDate(date: String?): LiveData<List<RecordData>> {
        return appDatabase.recordDataDao().getRecordsFromDate(date)
    }

    override fun getAllDates(): LiveData<List<String>> {
        return appDatabase.recordDataDao().getAllDates()
    }

    override fun getRecordsInGroup(): LiveData<List<RecordData>> {
        return appDatabase.recordDataDao().getAllRecordsInGroup()
    }

    override fun searchRecord(query: String?, date: String?): Observable<List<RecordData>> {
        return Observable.fromCallable {
            return@fromCallable appDatabase.recordDataDao().searchRecord(query, date)
        }
    }

    override fun getRecords(): LiveData<List<RecordData>> {
        return appDatabase.recordDataDao().getRecords()
    }

    override fun insertRecord(recordData: RecordData): Observable<Boolean> {
        return Observable.fromCallable {
            appDatabase.recordDataDao().insert(recordData)
            return@fromCallable true
        }
    }
}