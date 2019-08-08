package com.example.contract.di.module

import android.app.Application
import com.example.contract.service.KafkaConnection
import com.example.contract.di.scope.PerApplication
import com.example.contract.service.RealmUser
import com.example.mvvmkotlin.MyApp
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Module
class ApplicationModule(private val myApp: MyApp) {

    @Provides
    @Singleton
    @PerApplication
    fun provideApplication(): Application {
        return myApp
    }

    @Provides
    @Singleton
    @PerApplication
    fun provideKafka(): KafkaConnection {
        return KafkaConnection()
    }

    @Provides
    fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }

    @Provides
    fun provideRealmUser(realm: Realm): RealmUser {
        return RealmUser(realm)
    }
}