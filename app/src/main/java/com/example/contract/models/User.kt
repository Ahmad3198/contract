package com.example.contract.models


import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey



class User : RealmModel {
    @PrimaryKey
    var id: Int = 0

    var username: String? = null
}