package com.uttampanchasara.scanner

import android.arch.lifecycle.ViewModel
import com.uttampanchasara.scanner.data.DataManager
import com.uttampanchasara.scanner.ui.base.BaseView
import com.uttampanchasara.scanner.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Uttam Panchasara on 26-11-2018.
 */
open class BaseViewModel(val mDataManager: DataManager,
                         val mSchedulerProvider: SchedulerProvider,
                         val mCompositeDisposable: CompositeDisposable) : ViewModel() {

    var view: BaseView? = null

    open fun isViewAttached(): Boolean {
        return view != null
    }

    open fun onAttachView(view: BaseView) {
        this.view = view
    }

    fun onDetachView() {
        mCompositeDisposable.dispose()
        view = null
    }

    open fun getBaseView(): BaseView? {
        return view
    }
}