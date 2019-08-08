package com.example.contract.ui.activity.main

import com.example.contract.service.RealmUser
import javax.inject.Inject


class UserPresenter {

    @Inject
    lateinit var realmUser: RealmUser

    fun getAll(){
//        realmUser.getAllUser()
    }
}