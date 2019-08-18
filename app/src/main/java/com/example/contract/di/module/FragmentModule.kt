package com.example.contract.di.module


import com.example.contract.util.CustomView
import com.example.gallerylibrary.ui.gallery.ChatListAdapter
import dagger.Module
import dagger.Provides

/**
 * Created by ogulcan on 07/02/2018.
 */
@Module
class FragmentModule {
    @Provides
    fun provideChatListAdapter(): ChatListAdapter {
        return ChatListAdapter()
    }

    @Provides
    fun provideCustomView(): CustomView {
        return CustomView()
    }
}