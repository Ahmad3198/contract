package com.example.contract.di.component

import com.example.mvvmkotlin.MyApp

object AppInjector {
    fun init(myApp: MyApp) {
        DaggerApplicationComponent.builder().application(myApp).build()
    }
}