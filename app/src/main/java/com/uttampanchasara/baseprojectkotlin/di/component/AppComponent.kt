package com.uttampanchasara.baseprojectkotlin.di.component

import android.app.Application
import android.content.Context
import com.uttampanchasara.baseprojectkotlin.AppController
import com.uttampanchasara.baseprojectkotlin.data.DataManager
import com.uttampanchasara.baseprojectkotlin.di.ApplicationContext
import com.uttampanchasara.baseprojectkotlin.di.module.AppModule
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