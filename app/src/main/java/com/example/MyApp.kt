package com.example.mvvmkotlin

import android.app.Application
import com.example.contract.BuildConfig
import com.example.contract.di.component.ActivityComponent
import com.example.contract.di.component.ApplicationComponent
import com.example.contract.di.component.DaggerApplicationComponent
import com.example.contract.di.module.ApplicationModule
import dagger.android.DaggerApplication
import io.realm.Realm
import rx_activity_result2.RxActivityResult

class MyApp : Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        RxActivityResult.register(this)
        Realm.init(this)
        instance = this
        setup()

        if (BuildConfig.DEBUG) {

        }
    }




    fun setup() {
        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this)).build()
        component.inject(this)
    }

    fun getApplicationComponent(): ApplicationComponent {
        return component
    }

    companion object {
        lateinit var instance: MyApp private set
    }
}