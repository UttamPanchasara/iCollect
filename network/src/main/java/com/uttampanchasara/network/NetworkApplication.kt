package com.uttampanchasara.network

import android.app.Application

/**
 * Created by Lalit on 20-Sep-18.
 */
class NetworkApplication : Application() {
    fun getContext() = applicationContext
}