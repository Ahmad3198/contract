package com.example.contract.di.component

import com.example.contract.di.module.FragmentModule
import com.example.contract.ui.fragment.chat.ChatListFragment
import dagger.Component

/**
 * Created by ogulcan on 07/02/2018.
 */
@Component(modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(chatListFragment: ChatListFragment)
}