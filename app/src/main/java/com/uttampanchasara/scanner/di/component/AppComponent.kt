package com.uttampanchasara.scanner.di.component

import android.app.Application
import android.content.Context
import com.uttampanchasara.scanner.AppController
import com.uttampanchasara.scanner.data.DataManager
import com.uttampanchasara.scanner.di.ApplicationContext
import com.uttampanchasara.scanner.di.module.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/15/2018
 */
@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun getDataManager(): DataManager

    fun inject(app: AppController)

}