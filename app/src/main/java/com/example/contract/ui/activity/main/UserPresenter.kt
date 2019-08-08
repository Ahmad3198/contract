package com.example.contract.ui.activity.main

import com.example.contract.service.RealmUser
import io.realm.Realm

class UserPresenter {

    val realmUser = RealmUser(Realm.getDefaultInstance())
    fun getAll(){
        realmUser.getAllUser()
    }
}