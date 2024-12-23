package com.example.project.currency.domain.usecase

import com.example.project.core.presenation.formatToTwoDecimals

/**
 * created by Karim Haggagi on 12/22/24
 **/
class CalculateExchangeRateUseCase {

    suspend operator fun invoke(
        amount: Double,
        fromCurrency: Double,
        toCurrency: Double
    ): String {

        if (fromCurrency == 0.0) {
            return "0.0"
        }
        // Convert Currency to EUR
        val amountInUSD = amount / fromCurrency

        // Convert EUR to Another Currency
        val result = (amountInUSD * toCurrency)
        return result.formatToTwoDecimals()

    }
}