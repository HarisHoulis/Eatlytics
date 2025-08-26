package com.harishoulis.eatlytics

import android.app.Application
import com.harishoulis.eatlytics.di.AppComponent
import com.harishoulis.eatlytics.di.DaggerAppComponent

class EatlyticsApplication : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}
