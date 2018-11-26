package com.uttampanchasara.network.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 * This class has two tasks:
 * 1) sign requests with the auth token, when available
 * 2) try to refresh a new token
 */


class AuthInterceptor : Interceptor {
    // these two static variables serve for the pattern to refresh a token
    //private final static ConditionVariable LOCK = new ConditionVariable(true);
    // private static final AtomicBoolean mIsRefreshing = new AtomicBoolean(false);
    // private static final long REFRESH_WAIT_TIMEOUT = 2 * 1000;
    // private static String REFRESH_TOKEN = "";

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        // Request customization: add request headers
        val request = chain.request()

        var request1 = request
        if (request.header("Add-Auth") != null) {
//            Log.e("AuthInterceptor", AUTH_TOKEN)
            request1 = request.newBuilder()
                    .addHeader("Authorization", "Bearer " + "")
//                    .addHeader("Token", "ccRhcqZC+edzvL/hVUTLF9MalrvdbFmdlzUF8tpHQD1X3W0P4jYiBnYfMChwCLqks/PHCIKzlpbaUbCuhcKSJQ==")
                    .removeHeader("Add-Auth")
                    .build()
        }
        // 2. proceed with the request
        return chain.proceed(request1)
    }
}
