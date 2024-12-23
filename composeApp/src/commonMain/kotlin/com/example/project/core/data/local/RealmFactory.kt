package com.example.project.core.data.local

import com.example.project.currency.data.local.entity.CurrencyEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration


/**
 * created by Karim Haggagi on 12/19/24
 **/
object RealmFactory {
    // use the RealmConfiguration.Builder() for more options
    fun create(): Realm {
        val configuration = RealmConfiguration
            .Builder(schema = setOf(CurrencyEntity::class))
            .compactOnLaunch()
            .build()
        val realm = Realm.open(configuration)
        return realm
    }

}