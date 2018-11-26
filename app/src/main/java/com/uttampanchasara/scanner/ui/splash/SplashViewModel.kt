package com.uttampanchasara.scanner.ui.splash

import com.uttampanchasara.scanner.BaseViewModel
import com.uttampanchasara.scanner.data.DataManager
import com.uttampanchasara.scanner.ui.base.BaseView
import com.uttampanchasara.scanner.utils.rx.SchedulerProvider
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