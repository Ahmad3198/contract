package com.example.contract.di.module

import android.app.Application
import com.example.contract.di.scope.PerApplication
import com.example.mvvmkotlin.MyApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val myApp: MyApp) {

    @Provides
    @Singleton
    @PerApplication
    fun provideApplication(): Application {
        return myApp
    }
}