package com.uttampanchasara.icollect.ui.dashboard

import android.util.Log
import com.uttampanchasara.icollect.BaseViewModel
import com.uttampanchasara.icollect.data.DataManager
import com.uttampanchasara.icollect.data.repository.record.RecordData
import com.uttampanchasara.icollect.ui.base.BaseView
import com.uttampanchasara.icollect.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * @since 12/11/2018
 */
class DashboardViewModel
@Inject constructor(mDataManager: DataManager,
                    mSchedulerProvider: SchedulerProvider,
                    mCompositeDisposable: CompositeDisposable) : BaseViewModel(mDataManager, mSchedulerProvider, mCompositeDisposable) {

    companion object {
        val TAG = "DashboardViewModel"
    }

    var mView: DashboardView? = null

    override fun onAttachView(view: BaseView) {
        mView = view as DashboardView
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