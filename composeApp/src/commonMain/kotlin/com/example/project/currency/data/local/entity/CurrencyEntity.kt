package com.example.project.currency.data.local.entity

import com.example.project.currency.domain.model.Country
import com.example.project.currency.domain.model.CurrencyDetails
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

/**
 * created by Karim Haggagi on 12/19/24
 **/
open class CurrencyEntity : RealmObject {
    var code: String = ""
    var value: Double = 0.0

    @PrimaryKey
    var _id: ObjectId = ObjectId()
}

fun CurrencyEntity.asDomain(): CurrencyDetails {
    return CurrencyDetails(
        code = this.code,
        rate = this.value,
        country = Country.fromCode(code) ?: Country.DEFAULT
    )
}