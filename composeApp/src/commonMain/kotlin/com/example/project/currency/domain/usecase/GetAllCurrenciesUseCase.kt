package com.example.project.currency.domain.usecase

import com.example.project.core.domain.DataError
import com.example.project.core.domain.Result
import com.example.project.core.domain.map
import com.example.project.currency.domain.model.CurrencyModel
import com.example.project.currency.domain.repository.CurrencyRepository

/**
 * created by Karim Haggagi on 12/18/24
 **/
class GetAllCurrenciesUseCase(private val repository: CurrencyRepository) {
    suspend operator fun invoke(): Result<CurrencyModel, DataError.Remote> {
        return repository.getAllCurrencies().map { currencyModel ->
            val filteredData =
                currencyModel.currencies.filter { true }

            CurrencyModel(
                lastUpdated = currencyModel.lastUpdated,
                currencies = filteredData
            )
        }
    }
}