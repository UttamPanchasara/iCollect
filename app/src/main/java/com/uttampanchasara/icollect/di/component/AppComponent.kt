package com.uttampanchasara.icollect.di.component

import android.app.Application
import android.content.Context
import com.uttampanchasara.icollect.AppController
import com.uttampanchasara.icollect.data.DataManager
import com.uttampanchasara.icollect.di.ApplicationContext
import com.uttampanchasara.icollect.di.module.AppModule
import dagger.Component
import io.socket.client.Socket
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

    fun getSocket(): Socket

    fun inject(app: AppController)

}