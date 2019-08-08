package com.example.mvvmkotlin

import android.app.Activity
import android.app.Application
import com.example.contract.di.component.AppInjector
import com.example.contract.di.component.DaggerApplicationComponent

import io.realm.Realm
import rx_activity_result2.RxActivityResult
import io.realm.RealmConfiguration


class MyApp : Application(){

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        initRealmConfiguration()
        RxActivityResult.register(this)
        AppInjector.init(this)

    }

    fun setup() {
        val component = DaggerApplicationComponent.builder().application(this).build()
        component.inject(this)
        instance = this
    }

    private fun initRealmConfiguration() {
       val realmConfiguration = RealmConfiguration.Builder()
            .name("android.contract")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }

    companion object {
        lateinit var instance: MyApp private set
    }
}