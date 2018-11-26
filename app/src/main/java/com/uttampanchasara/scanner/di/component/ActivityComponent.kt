package com.uttampanchasara.scanner.di.component

import com.uttampanchasara.scanner.di.PerActivity
import com.uttampanchasara.scanner.di.module.ActivityModule
import com.uttampanchasara.scanner.ui.addnew.AddNewActivity
import com.uttampanchasara.scanner.ui.dashboard.DashboardActivity
import com.uttampanchasara.scanner.ui.splash.SplashActivity
import dagger.Component

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/15/2018
 */
@PerActivity
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(activity: SplashActivity)

    fun inject(activity: DashboardActivity)

    fun inject(addNewActivity: AddNewActivity)
}