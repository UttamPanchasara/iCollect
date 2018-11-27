package com.uttampanchasara.scanner.ui.addnew

import com.uttampanchasara.scanner.BaseViewModel
import com.uttampanchasara.scanner.data.DataManager
import com.uttampanchasara.scanner.data.repository.record.RecordData
import com.uttampanchasara.scanner.getDate
import com.uttampanchasara.scanner.ui.base.BaseView
import com.uttampanchasara.scanner.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * @since 11/26/2018
 */
class AddNewViewModel
@Inject constructor(mDataManager: DataManager,
                    mSchedulerProvider: SchedulerProvider,
                    mCompositeDisposable: CompositeDisposable) : BaseViewModel(mDataManager, mSchedulerProvider, mCompositeDisposable) {

    var mView: AddNewView? = null

    override fun onAttachView(view: BaseView) {
        mView = view as AddNewView
    }

    fun saveRecord(time: Long, code: String, name: String, number: String, address: String) {

        val time = System.currentTimeMillis()
        val data = RecordData(time, getDate(time), address, code, name, number)

        mCompositeDisposable.add(mDataManager.insertRecord(data)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe({
                    mView?.onRecordInserted()
                }, {
                    mView?.onError(it.message!!)
                }))
    }
}