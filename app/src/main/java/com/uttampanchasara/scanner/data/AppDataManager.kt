package com.uttampanchasara.scanner.data

import android.arch.lifecycle.LiveData
import android.content.Context
import com.uttampanchasara.scanner.data.repository.record.RecordData
import com.uttampanchasara.scanner.di.ApplicationContext
import com.uttampanchasara.network.remote.ApiServices
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/15/2018
 */
@Singleton
class AppDataManager
@Inject internal constructor(@ApplicationContext val context: Context,
                             val dbHelper: DbHelper,
                             val apiServices: ApiServices) : DataManager {

    override fun searchRecord(query: String?): Observable<List<RecordData>> {
        return dbHelper.searchRecord(query)
    }

    override fun getRecords(): LiveData<List<RecordData>> {
        return dbHelper.getRecords()
    }

    override fun insertRecord(recordData: RecordData): Observable<Boolean> {
        return dbHelper.insertRecord(recordData)
    }
}