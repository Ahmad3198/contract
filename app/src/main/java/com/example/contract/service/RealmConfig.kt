package com.example.contract.service

import io.realm.Realm
import io.realm.RealmConfiguration

class RealmConfig {
    init {
        val realmConfiguration = RealmConfiguration.Builder()
            .name("android.contract")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }
}