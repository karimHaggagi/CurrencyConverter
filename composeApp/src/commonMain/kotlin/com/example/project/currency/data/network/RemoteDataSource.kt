package com.example.project.currency.data.network

import com.example.project.core.domain.DataError
import com.example.project.core.domain.Result
import com.example.project.currency.data.network.dto.CurrencyDTO

/**
 * created by Karim Haggagi on 12/15/24
 **/
interface RemoteDataSource {
    suspend fun getAllCurrencies(): Result<CurrencyDTO, DataError.Remote>
    suspend fun getCurrencyByDate(currency: String, date: String): Result<CurrencyDTO, DataError.Remote>

}