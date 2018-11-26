package com.uttampanchasara.scanner

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.uttampanchasara.scanner.data.DataManager
import com.uttampanchasara.scanner.di.component.DaggerAppComponent
import com.uttampanchasara.scanner.di.module.AppModule
import com.uttampanchasara.scanner.utils.PrefUtils
import com.uttampanchasara.network.remote.ApiClient
import com.uttampanchasara.network.remote.ApiServices
import javax.inject.Inject


/**
 * Created by Bhavik Makwana on 10-09-2018.
 */
class AppController : MultiDexApplication() {

    companion object {
        lateinit var mAppController: AppController
        lateinit var mServices: ApiServices
    }

    @set:Inject
    internal var mDataManager: DataManager? = null

    lateinit var mAppComponent: DaggerAppComponent

    override fun onCreate() {
        super.onCreate()
        PrefUtils.init(this)
        mAppController = this
        mServices = ApiClient().getApiServices()

        mAppComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build() as DaggerAppComponent

        mAppComponent.inject(this)
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }
}