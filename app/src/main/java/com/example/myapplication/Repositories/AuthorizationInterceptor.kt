package com.example.myapplication.Repositories

import okhttp3.Interceptor
import okhttp3.Response
import android.app.Application;
import android.content.Context;


class AuthorizationInterceptor : Interceptor, Application(){
    override fun onCreate() {
        super.onCreate()
    }

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        // MyApplication.appContext.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE).getString("USER_TOKEN","")
        proceed(
            request()
                .newBuilder()
                .addHeader("Authorization", applicationContext.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE).getString("USER_TOKEN",""))
                .build()
        )
    }
}