package com.uttampanchasara.icollect.ui.splash

import android.content.Intent
import com.uttampanchasara.icollect.R
import com.uttampanchasara.icollect.data.DataManager
import com.uttampanchasara.icollect.di.component.ActivityComponent
import com.uttampanchasara.icollect.ui.base.BaseActivity
import com.uttampanchasara.icollect.ui.dashboard.DashboardActivity
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashView {
    override fun getLayout(): Int {
        return R.layout.activity_splash
    }

    @Inject
    lateinit var mDataManager: DataManager

    @Inject
    lateinit var mViewModel: SplashViewModel

    override fun injectComponents(mActivityComponent: ActivityComponent) {
        mActivityComponent.inject(this)
    }

    override fun setUp() {
        mViewModel.onAttachView(this)
        mViewModel.navigateToSignIn()
    }

    override fun navigateToDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }
}