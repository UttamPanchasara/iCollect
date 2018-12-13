package com.uttampanchasara.icollect.data

import android.arch.lifecycle.LiveData
import android.content.Context
import com.uttampanchasara.icollect.data.repository.record.RecordData
import com.uttampanchasara.icollect.data.repository.user.User
import com.uttampanchasara.icollect.di.ApplicationContext
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
    override fun getUsers(): LiveData<List<User>> {
        return dbHelper.getUsers()
    }

    override fun insertUsers(user: List<User>): Observable<Boolean> {
        return dbHelper.insertUsers(user)
    }

    override fun getRecordsFromDate(date: String?): Observable<List<RecordData>> {
        return dbHelper.getRecordsFromDate(date)
    }

    override fun getAllRecords(): Observable<List<RecordData>> {
        return dbHelper.getAllRecords()
    }

    override fun getLiveRecordsFromDate(date: String?): LiveData<List<RecordData>> {
        return dbHelper.getLiveRecordsFromDate(date)
    }

    override fun getAllDates(): LiveData<List<String>> {
        return dbHelper.getAllDates()
    }

    override fun getRecordsInGroup(): LiveData<List<RecordData>> {
        return dbHelper.getRecordsInGroup()
    }

    override fun searchRecord(query: String?, date: String?): Observable<List<RecordData>> {
        return dbHelper.searchRecord(query, date)
    }

    override fun getRecords(): LiveData<List<RecordData>> {
        return dbHelper.getRecords()
    }

    override fun insertRecord(recordData: RecordData): Observable<Boolean> {
        return dbHelper.insertRecord(recordData)
    }
}