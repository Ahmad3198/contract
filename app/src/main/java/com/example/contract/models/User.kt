package com.example.contract.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User : RealmObject(){
@PrimaryKey
    var id: Int = 0
    var username: String? = null
}