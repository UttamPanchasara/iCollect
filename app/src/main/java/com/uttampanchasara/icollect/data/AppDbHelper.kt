package com.uttampanchasara.icollect.data

import android.arch.lifecycle.LiveData
import com.uttampanchasara.icollect.data.repository.record.RecordData
import com.uttampanchasara.icollect.data.repository.user.User
import io.reactivex.Observable
import javax.inject.Inject

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/15/2018
 */
class AppDbHelper
@Inject internal constructor(private val appDatabase: AppDatabase) : DbHelper {
    override fun getUsers(): LiveData<List<User>> {
        return appDatabase.userDao().getLiveUser()
    }

    override fun insertUsers(user: List<User>): Observable<Boolean> {
        return Observable.fromCallable {
            appDatabase.userDao().insertAll(user)
            return@fromCallable true
        }
    }

    override fun getRecordsFromDate(date: String?): Observable<List<RecordData>> {
        return Observable.fromCallable {
            return@fromCallable appDatabase.recordDataDao().getRecordsFromDate(date)
        }
    }

    override fun getAllRecords(): Observable<List<RecordData>> {
        return Observable.fromCallable {
            return@fromCallable appDatabase.recordDataDao().getAllRecords()
        }
    }

    override fun getLiveRecordsFromDate(date: String?): LiveData<List<RecordData>> {
        return appDatabase.recordDataDao().getLiveRecordsFromDate(date)
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