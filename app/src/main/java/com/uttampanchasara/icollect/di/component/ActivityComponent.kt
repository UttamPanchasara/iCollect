package com.uttampanchasara.icollect.di.component

import com.uttampanchasara.icollect.di.PerActivity
import com.uttampanchasara.icollect.di.module.ActivityModule
import com.uttampanchasara.icollect.ui.addnew.AddNewActivity
import com.uttampanchasara.icollect.ui.chat.ChatActivity
import com.uttampanchasara.icollect.ui.dashboard.DashboardActivity
import com.uttampanchasara.icollect.ui.record.RecordActivity
import com.uttampanchasara.icollect.ui.splash.SplashActivity
import com.uttampanchasara.icollect.ui.users.UsersActivity
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

    fun inject(activity: RecordActivity)

    fun inject(addNewActivity: AddNewActivity)

    fun inject(dashboardActivity: DashboardActivity)

    fun inject(usersActivity: UsersActivity)

    fun inject(chatActivity: ChatActivity)
}