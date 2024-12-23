package com.example.project.currency.domain.repository

import com.example.project.core.domain.DataError
import com.example.project.core.domain.Result
import com.example.project.currency.data.local.entity.CurrencyEntity
import com.example.project.currency.domain.model.CurrencyDetails
import com.example.project.currency.domain.model.CurrencyModel
import kotlinx.coroutines.flow.Flow

/**
 * created by Karim Haggagi on 11/7/24
 **/
interface CurrencyRepository {
    suspend fun getAllCurrencies(): Result<CurrencyModel, DataError.Remote>
    fun getCashedCurrencies(): Flow<CurrencyModel>
    suspend fun getCurrencyByDate(currency: String, date: String): Result<CurrencyModel, DataError.Remote>
}