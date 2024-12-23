package com.example.project.currency.data.repoImpl

import com.example.project.core.domain.DataError
import com.example.project.core.domain.Result
import com.example.project.core.domain.map
import com.example.project.currency.data.local.LocalDataSource
import com.example.project.currency.data.local.entity.asDomain
import com.example.project.currency.data.network.RemoteDataSource
import com.example.project.currency.data.network.dto.toCurrencyDomainModel
import com.example.project.currency.domain.model.Country
import com.example.project.currency.domain.model.CurrencyModel
import com.example.project.currency.domain.model.asEntity
import com.example.project.currency.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


/**
 * created by Karim Haggagi on 11/7/24
 **/

class CurrencyRepoImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : CurrencyRepository {
    override suspend fun getAllCurrencies(): Result<CurrencyModel, DataError.Remote> {
        return remoteDataSource.getAllCurrencies().map {
            val model = it.toCurrencyDomainModel()
            localDataSource.clearData()
            localDataSource.saveLastUpdate(model.lastUpdated)
            model.currencies.forEach {
                if (Country.fromCode(it.code) != null) {
                    localDataSource.saveCurrency(it.asEntity())
                }
            }
            model
        }
    }

    override fun getCashedCurrencies(): Flow<CurrencyModel> {
        return localDataSource.getCurrencies().map { item ->
            CurrencyModel(
                lastUpdated = localDataSource.getLastUpdate(),
                currencies = item.map { it.asDomain() }
            )
        }
    }

    override suspend fun getCurrencyByDate(currency: String, date: String): Result<CurrencyModel, DataError.Remote> {
        return remoteDataSource.getCurrencyByDate(currency, date).map { it.toCurrencyDomainModel() }
    }
}






















