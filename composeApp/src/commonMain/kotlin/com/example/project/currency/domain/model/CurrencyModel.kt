package com.example.project.currency.domain.model

import com.example.project.core.presenation.formatDateTime
import com.example.project.currency.data.local.entity.CurrencyEntity
import com.example.project.currency.presentation.currencyScreen.CurrencyContract


/**
 * created by Karim Haggagi on 12/15/24
 **/
data class CurrencyModel(
    val lastUpdated: String,
    val currencies: List<CurrencyDetails>
)

data class CurrencyDetails(
    val country: Country,
    val code: String,
    val rate: Double
)

fun CurrencyDetails.asEntity(): CurrencyEntity {
    val entity = CurrencyEntity()
    entity.code = this.code
    entity.value = this.rate
    return entity
}

fun CurrencyModel.asLatestCurrencyDetails(
    fromCurrency: CurrencyDetails,
    toCurrency: CurrencyDetails
): CurrencyContract.LatestCurrencyDetails {
    return CurrencyContract.LatestCurrencyDetails(
        lastUpdate = this.lastUpdated.formatDateTime(),
        toCurrencyDetails = this.currencies.first { it.country == toCurrency.country },
        fromCurrencyDetails = this.currencies.first { it.country == fromCurrency.country }
    )
}