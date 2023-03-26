package com.yosuahaloho.mypropergitap.utils

import android.app.Application
import leakcanary.LeakCanary
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

    }
}