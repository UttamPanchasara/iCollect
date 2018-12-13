package com.uttampanchasara.icollect.ui.dashboard

import android.util.Log
import com.uttampanchasara.icollect.BaseViewModel
import com.uttampanchasara.icollect.data.DataManager
import com.uttampanchasara.icollect.data.repository.record.RecordData
import com.uttampanchasara.icollect.data.repository.user.User
import com.uttampanchasara.icollect.getTimeWithTFormat
import com.uttampanchasara.icollect.ui.base.BaseView
import com.uttampanchasara.icollect.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

/**
 * @since 12/11/2018
 */
class DashboardViewModel
@Inject constructor(mDataManager: DataManager,
                    mSchedulerProvider: SchedulerProvider,
                    mCompositeDisposable: CompositeDisposable,
                    val mSocket: Socket) : BaseViewModel(mDataManager, mSchedulerProvider, mCompositeDisposable) {

    companion object {
        val TAG = "DashboardViewModel"
    }

    var mView: DashboardView? = null

    override fun onAttachView(view: BaseView) {
        mView = view as DashboardView

        // broadcast event to load users
        mSocket.emit("load_users", "")

        mSocket.on("new_record") { args ->
            try {
                val recordObject = args[0] as JSONObject
                Log.d(DashboardActivity.TAG, recordObject.toString())

                var createdTime: Long = 0
                val createdAt = recordObject.getString("createdAt")
                if (!createdAt.isNullOrEmpty()) {
                    createdTime = getTimeWithTFormat(createdAt)
                }
                val recordData = RecordData(createdTime,
                        recordObject.getString("createdDate"),
                        recordObject.getString("customerAddress"),
                        recordObject.getString("productId"),
                        recordObject.getString("customerName"),
                        recordObject.getString("customerNumber"))

                insertRecord(recordData)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        mSocket.on("load_users") {
            try {
                val userList = arrayListOf<User>()
                val usersArray = it[0] as JSONArray
                for (i in 0..(usersArray.length() - 1)) {
                    val user = usersArray[i] as JSONObject
                    val id = user.getInt("id")
                    val userName = user.getString("userName")
                    val userEmail = user.getString("userEmail")
                    val userNumber = user.getString("userNumber")
                    val createdAt = user.getString("createdAt")

                    userList.add(User(id, userName, userEmail, userNumber, createdAt))
                }

                insertUsers(userList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun insertUsers(users: List<User>) {
        mCompositeDisposable.add(mDataManager.insertUsers(users)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe({
                    Log.d(TAG, "Users Inserted")
                }, {
                    it.printStackTrace()
                }))
    }

    fun insertRecord(recordData: RecordData) {
        mCompositeDisposable.add(mDataManager.insertRecord(recordData)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe({
                    Log.d(TAG, "Record Inserted")
                }, {
                    Log.e(TAG, it.message)
                }))
    }

    fun getRecordsFromDate(mCurrentDate: String) {
        mCompositeDisposable.add(mDataManager.getRecordsFromDate(mCurrentDate)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe({
                    var recordOfToday = 0
                    if (it?.isNotEmpty()!!) {
                        recordOfToday = it.size
                    }

                    mView?.onRecordOfToday(recordOfToday)
                }, {
                    mView?.onRecordOfToday(0)
                }))
    }
}