package com.uttampanchasara.baseprojectkotlin

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.uttampanchasara.baseprojectkotlin.data.DataManager
import com.uttampanchasara.baseprojectkotlin.di.component.DaggerAppComponent
import com.uttampanchasara.baseprojectkotlin.di.module.AppModule
import com.uttampanchasara.baseprojectkotlin.utils.PrefUtils
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