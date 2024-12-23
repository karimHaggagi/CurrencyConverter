package com.example.project.currency.data.network

import com.example.project.core.data.network.safeCall
import com.example.project.core.domain.DataError
import com.example.project.core.domain.Result
import com.example.project.currency.data.network.NetworkConfig.BASE_URL
import com.example.project.currency.data.network.dto.CurrencyDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.get

/**
 * created by Karim Haggagi on 12/15/24
 **/

class KtorRemoteDataSource(private val httpClient: HttpClient) : RemoteDataSource {

    override suspend fun getAllCurrencies(): Result<CurrencyDTO, DataError.Remote> {
        return safeCall<CurrencyDTO> {
            httpClient.get("$BASE_URL/v3/latest/")
        }
    }

    override suspend fun getCurrencyByDate(
        currency: String,
        date: String
    ): Result<CurrencyDTO, DataError.Remote> {
        return safeCall <CurrencyDTO>{
            httpClient.get("$BASE_URL/v3/historical"){
                url {
                    parameters.append("currencies", currency)
                    parameters.append("date", date)
                }

            }
        }
    }
}