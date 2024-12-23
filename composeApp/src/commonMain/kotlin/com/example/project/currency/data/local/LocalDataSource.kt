package com.example.project.currency.data.local

import com.example.project.currency.data.local.entity.CurrencyEntity
import kotlinx.coroutines.flow.Flow

/**
 * created by Karim Haggagi on 12/18/24
 **/
interface LocalDataSource {
    suspend fun saveLastUpdate(lastUpdate: String)
    suspend fun getLastUpdate(): String
//    suspend fun isDataFresh(currentTimeStamp: Long): Boolean
    suspend fun saveCurrency(currency: CurrencyEntity)
    fun getCurrencies(): Flow<List<CurrencyEntity>>
    suspend fun clearData()

}