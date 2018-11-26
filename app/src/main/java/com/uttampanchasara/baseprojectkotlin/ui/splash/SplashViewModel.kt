package com.uttampanchasara.baseprojectkotlin.ui.splash

import com.uttampanchasara.baseprojectkotlin.BaseViewModel
import com.uttampanchasara.baseprojectkotlin.data.DataManager
import com.uttampanchasara.baseprojectkotlin.ui.base.BaseView
import com.uttampanchasara.baseprojectkotlin.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/20/2018
 */
class SplashViewModel
@Inject constructor(mDataManager: DataManager,
                    mSchedulerProvider: SchedulerProvider,
                    mCompositeDisposable: CompositeDisposable) : BaseViewModel(mDataManager, mSchedulerProvider, mCompositeDisposable) {

    var mView: SplashView? = null

    override fun onAttachView(view: BaseView) {
        mView = view as SplashView
    }

    fun navigateToSignIn() {
        mView?.navigateToDashboard()
    }
}