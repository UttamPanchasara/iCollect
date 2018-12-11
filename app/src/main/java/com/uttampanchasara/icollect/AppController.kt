package com.uttampanchasara.icollect

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import android.util.Log
import com.uttampanchasara.icollect.data.DataManager
import com.uttampanchasara.icollect.di.component.DaggerAppComponent
import com.uttampanchasara.icollect.di.module.AppModule
import com.uttampanchasara.icollect.utils.PrefUtils
import com.uttampanchasara.network.remote.ApiClient
import com.uttampanchasara.network.remote.ApiServices
import io.socket.client.Socket
import javax.inject.Inject


/**
 * Created by Bhavik Makwana on 10-09-2018.
 */
class AppController : MultiDexApplication() {

    companion object {
        lateinit var mAppController: AppController
        lateinit var mServices: ApiServices

        val TAG = "AppController"
    }

    @set:Inject
    internal var mDataManager: DataManager? = null

    lateinit var mAppComponent: DaggerAppComponent

    @Inject
    lateinit var mSocket: Socket

    override fun onCreate() {
        super.onCreate()
        PrefUtils.init(this)
        mAppController = this
        mServices = ApiClient().getApiServices()

        mAppComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build() as DaggerAppComponent

        mAppComponent.inject(this)

        // connect to server
        mSocket.on(Socket.EVENT_CONNECT) {
            Log.i(TAG, "connected")
        }.on(Socket.EVENT_DISCONNECT) {
            Log.e(TAG, "disconnected")
        }
        mSocket.connect()
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }
}