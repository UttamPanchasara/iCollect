package com.uttampanchasara.baseprojectkotlin.data

import android.arch.lifecycle.LiveData
import android.content.Context
import com.uttampanchasara.baseprojectkotlin.data.repository.record.RecordData
import com.uttampanchasara.baseprojectkotlin.di.ApplicationContext
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
    override fun getRecords(): LiveData<List<RecordData>> {
        return dbHelper.getRecords()
    }

    override fun insertRecord(recordData: RecordData): Observable<Boolean> {
        return dbHelper.insertRecord(recordData)
    }
}