package com.uttampanchasara.baseprojectkotlin.ui.addnew

import com.uttampanchasara.baseprojectkotlin.BaseViewModel
import com.uttampanchasara.baseprojectkotlin.data.DataManager
import com.uttampanchasara.baseprojectkotlin.data.repository.record.RecordData
import com.uttampanchasara.baseprojectkotlin.ui.base.BaseView
import com.uttampanchasara.baseprojectkotlin.utils.rx.SchedulerProvider
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

    fun saveRecord(code: String, name: String, number: String, address: String) {

        val data = RecordData(System.currentTimeMillis(), address, code, name, number)

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