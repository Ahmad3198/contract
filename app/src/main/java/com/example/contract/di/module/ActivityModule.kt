package com.example.contract.di.module

import android.app.Activity
import com.example.contract.service.KafkaConnection
import com.example.contract.service.RealmUser
import com.example.contract.ui.activity.main.MainContract
import com.example.contract.ui.activity.main.MainPresenter
import com.example.contract.ui.activity.main.UserPresenter
import com.example.gallerylibrary.ui.gallery.ImageCollectionAdapter
import dagger.Module
import dagger.Provides
import io.realm.Realm

@Module
class ActivityModule(private var activity: Activity) {

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    fun providePresenter(): MainContract.Presenter {
        return MainPresenter()
    }

    @Provides
    fun provideCollectionAdapter(): ImageCollectionAdapter {
        return ImageCollectionAdapter()
    }

    @Provides
    fun provideUserPresenter(): UserPresenter {
        return UserPresenter()
    }

    @Provides
    fun provideKafka(): KafkaConnection {
        return KafkaConnection()
    }
}
