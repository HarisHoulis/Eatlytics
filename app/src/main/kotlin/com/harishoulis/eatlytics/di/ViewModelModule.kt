package com.harishoulis.eatlytics.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.harishoulis.eatlytics.presentation.viewmodel.AppViewModelFactory
import com.harishoulis.eatlytics.presentation.viewmodel.DashboardViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ClassKey(DashboardViewModel::class)
    abstract fun bindDashboardViewModel(vm: DashboardViewModel): ViewModel
}
