package com.example.contract.di.module

import android.app.Activity
import com.example.contract.ui.activity.main.MainContract
import com.example.contract.ui.activity.main.MainPresenter
import com.example.gallerylibrary.manager.GalleryManager
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
}