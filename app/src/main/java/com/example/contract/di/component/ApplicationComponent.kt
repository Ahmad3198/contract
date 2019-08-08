package com.example.contract.di.component

import com.example.contract.di.module.ApplicationModule
import com.example.mvvmkotlin.MyApp
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class
        ,ApplicationModule::class
    ])
interface ApplicationComponent {
    fun inject(application: MyApp)
}