package com.uttampanchasara.baseprojectkotlin.data

import android.arch.lifecycle.LiveData
import com.uttampanchasara.baseprojectkotlin.data.repository.record.RecordData
import io.reactivex.Observable
import javax.inject.Inject

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/15/2018
 */
class AppDbHelper
@Inject internal constructor(private val appDatabase: AppDatabase) : DbHelper {
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