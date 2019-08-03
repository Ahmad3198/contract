package com.example.contract.di.component

import com.example.contract.di.module.ActivityModule
import com.example.contract.ui.activity.main.MainActivity
import dagger.Component


@Component(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
}