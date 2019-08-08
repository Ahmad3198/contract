package com.example.contract.di.module

import android.app.Application
import com.example.contract.service.KafkaConnection
import com.example.contract.service.RealmUser
import com.example.mvvmkotlin.MyApp
import dagger.Module
import dagger.Provides
import io.realm.Realm

@Module
class ApplicationModule {

    @Provides
    fun provideApplication(myApp: MyApp): Application {
        return myApp
    }

    @Provides
    fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }
}