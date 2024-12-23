package com.example.project.currency.data.network.dto

import com.example.project.currency.domain.model.Country
import com.example.project.currency.domain.model.CurrencyDetails
import com.example.project.currency.domain.model.CurrencyModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * created by Karim Haggagi on 11/7/24
 **/
@Serializable
data class CurrencyDTO(
    val meta: MetaData? = null,
    val data: Map<String, Currency>? = null
)

@Serializable
data class MetaData(
    @SerialName("last_updated_at")
    val lastUpdatedAt: String? = null
)

@Serializable
data class Currency(
    val code: String? = null,
    val value: Double? = null
)

fun CurrencyDTO.toCurrencyDomainModel() =
    CurrencyModel(
        lastUpdated = meta?.lastUpdatedAt ?: "",
        currencies = data?.map { item ->
            CurrencyDetails(
                code = item.key,
                country = Country.fromCode(item.key)?:Country.DEFAULT,
                rate = item.value.value ?: 0.0
            )
        } ?: emptyList()
    )

