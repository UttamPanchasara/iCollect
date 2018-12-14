package com.uttampanchasara.icollect.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.uttampanchasara.icollect.data.DataManager
import com.uttampanchasara.icollect.di.ActivityContext
import com.uttampanchasara.icollect.di.PerActivity
import com.uttampanchasara.icollect.ui.addnew.AddNewViewModel
import com.uttampanchasara.icollect.ui.chat.ChatViewModel
import com.uttampanchasara.icollect.ui.dashboard.DashboardViewModel
import com.uttampanchasara.icollect.ui.record.RecordViewModel
import com.uttampanchasara.icollect.ui.splash.SplashViewModel
import com.uttampanchasara.icollect.utils.rx.AppSchedulerProvider
import com.uttampanchasara.icollect.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.socket.client.Socket

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/15/2018
 */
@Module
class ActivityModule constructor(val mActivity: AppCompatActivity) {

    @Provides
    @ActivityContext
    internal fun provideContext(): Context {
        return mActivity
    }

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    @PerActivity
    internal fun provideRecordViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable):
            RecordViewModel = RecordViewModel(dataManager, schedulerProvider, compositeDisposable)

    @Provides
    @PerActivity
    internal fun provideSplashViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable):
            SplashViewModel = SplashViewModel(dataManager, schedulerProvider, compositeDisposable)

    @Provides
    @PerActivity
    internal fun provideAddNewViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable):
            AddNewViewModel = AddNewViewModel(dataManager, schedulerProvider, compositeDisposable)

    @Provides
    @PerActivity
    internal fun provideDashboardViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable, socket: Socket):
            DashboardViewModel = DashboardViewModel(dataManager, schedulerProvider, compositeDisposable, socket)

    @Provides
    @PerActivity
    internal fun provideChatViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable, socket: Socket):
            ChatViewModel = ChatViewModel(dataManager, schedulerProvider, compositeDisposable, socket)

}