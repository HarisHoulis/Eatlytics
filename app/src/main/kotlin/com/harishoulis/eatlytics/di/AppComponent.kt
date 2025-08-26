package com.harishoulis.eatlytics.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    fun application(): Application

    fun viewModelFactory(): ViewModelProvider.Factory
}
