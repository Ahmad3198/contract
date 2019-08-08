package com.example.contract.service

import com.example.contract.models.User
import io.realm.Realm
import io.realm.RealmResults
import javax.inject.Singleton

@Singleton
class RealmUser(realm: Realm) {

    private var mRealm: Realm = realm

    fun closeRealm() {
        mRealm.close()
    }

    fun getAllUser(): RealmResults<User> {
        return mRealm.where(User::class.java).findAll()
    }

    fun addUserAsync(userName: String, onTransactionCallback: OnTransactionCallback) {

    }

    interface OnTransactionCallback {
        fun onRealmSuccess()
        fun onRealmError(e: Exception)
    }
}