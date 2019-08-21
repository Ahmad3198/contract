package com.example.contract.di.module


import com.example.contract.util.CustomView
import com.example.contract.ui.adapter.ChatListAdapter
import com.example.gallerylibrary.ui.gallery.ContractListAdapter
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
    fun provideContractListAdapter(): ContractListAdapter {
        return ContractListAdapter()
    }

    @Provides
    fun provideCustomView(): CustomView {
        return CustomView()
    }
}