package com.uttampanchasara.icollect.di.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.uttampanchasara.icollect.AppConstants
import com.uttampanchasara.icollect.data.*
import com.uttampanchasara.icollect.di.ApplicationContext
import com.uttampanchasara.network.remote.ApiClient
import com.uttampanchasara.network.remote.ApiServices
import dagger.Module
import dagger.Provides
import io.socket.client.IO
import io.socket.client.Socket
import javax.inject.Singleton

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/15/2018
 */
@Module
class AppModule constructor(val mApplication: Application) {

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return mApplication
    }

    @Provides
    internal fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    @Singleton
    internal fun provideApiService(): ApiServices {
        return ApiClient().getApiServices()
    }

    @Provides
    @Singleton
    internal fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, AppConstants.APP_DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()

    @Provides
    @Singleton
    internal fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    @Singleton
    internal fun provideDbHelper(appDbHelper: AppDbHelper): DbHelper {
        return appDbHelper
    }

    @Provides
    @Singleton
    internal fun provideSocket(): Socket {
        val options = IO.Options()
        options.reconnection = true
        options.reconnectionDelay = 500
        options.reconnectionAttempts = 10
        return IO.socket("http://192.168.1.56:3000", options)
    }
}