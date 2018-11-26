package com.uttampanchasara.scanner.ui.dashboard

import com.uttampanchasara.scanner.BaseViewModel
import com.uttampanchasara.scanner.data.DataManager
import com.uttampanchasara.scanner.ui.base.BaseView
import com.uttampanchasara.scanner.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/16/2018
 */
class DashboardViewModel
@Inject constructor(mDataManager: DataManager,
                    mSchedulerProvider: SchedulerProvider,
                    mCompositeDisposable: CompositeDisposable) : BaseViewModel(mDataManager, mSchedulerProvider, mCompositeDisposable) {
    var mView: DashboardView? = null

    override fun onAttachView(view: BaseView) {
        mView = view as DashboardView
    }

    fun searchRecord(query: String?) {

        mDataManager.searchRecord(query)
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribe {
                    mView?.onSearchResult(it)
                }
    }
}