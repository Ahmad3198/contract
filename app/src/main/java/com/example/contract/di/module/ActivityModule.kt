package com.example.contract.di.module

import android.app.Activity
import com.example.contract.service.KafkaConnection
import com.example.contract.ui.activity.main.MainContract
import com.example.contract.ui.activity.main.MainPresenter
import com.example.contract.ui.activity.main.UserPresenter
import com.example.contract.ui.adapter.PhotoCollectionAdapter
import dagger.Module
import dagger.Provides

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
    fun providePhotoCollectionAdapter(): PhotoCollectionAdapter {
        return PhotoCollectionAdapter()
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
