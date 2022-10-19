package com.testtask.androidassignmentone.core

import android.app.Application
import com.testtask.androidassignmentone.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AndroidAssignApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Koin
        startKoin {
            androidContext(this@AndroidAssignApp)
            modules(appModule)
        }
    }
}