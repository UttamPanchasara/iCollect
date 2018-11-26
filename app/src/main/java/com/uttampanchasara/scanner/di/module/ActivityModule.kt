package com.uttampanchasara.scanner.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.uttampanchasara.scanner.data.DataManager
import com.uttampanchasara.scanner.di.ActivityContext
import com.uttampanchasara.scanner.di.PerActivity
import com.uttampanchasara.scanner.ui.addnew.AddNewViewModel
import com.uttampanchasara.scanner.ui.dashboard.DashboardViewModel
import com.uttampanchasara.scanner.ui.splash.SplashViewModel
import com.uttampanchasara.scanner.utils.rx.AppSchedulerProvider
import com.uttampanchasara.scanner.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

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
    internal fun provideDashboardViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable):
            DashboardViewModel = DashboardViewModel(dataManager, schedulerProvider, compositeDisposable)

    @Provides
    @PerActivity
    internal fun provideSplashViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable):
            SplashViewModel = SplashViewModel(dataManager, schedulerProvider, compositeDisposable)

    @Provides
    @PerActivity
    internal fun provideAddNewViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable):
            AddNewViewModel = AddNewViewModel(dataManager, schedulerProvider, compositeDisposable)

}