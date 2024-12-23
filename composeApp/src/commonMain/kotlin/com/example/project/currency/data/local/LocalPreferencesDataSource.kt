package com.example.project.currency.data.local

import com.example.project.currency.data.local.entity.CurrencyEntity
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * created by Karim Haggagi on 12/18/24
 **/
@OptIn(ExperimentalSettingsApi::class)
class LocalPreferencesDataSource(settings: Settings, private val realm: Realm) :
    LocalDataSource {

    private val flowSettings: FlowSettings = (settings as ObservableSettings).toFlowSettings()

    override suspend fun saveLastUpdate(lastUpdate: String) {
        flowSettings.putString(LAST_UPDATE, lastUpdate)
    }

    override suspend fun getLastUpdate(): String {
        return flowSettings.getString(LAST_UPDATE, "")
    }

//    override suspend fun isDataFresh(currentTimeStamp: Long): Boolean {
//        val savedTimeStamp = flowSettings.getLong(LAST_UPDATE, 0L)
//
//        return if (savedTimeStamp != 0L) {
//            val currentInstant = Instant.fromEpochMilliseconds(currentTimeStamp)
//            val savedInstant = Instant.fromEpochMilliseconds(savedTimeStamp)
//
//            val currentDateTime = currentInstant.toLocalDateTime(TimeZone.currentSystemDefault())
//            val savedDateTime = savedInstant.toLocalDateTime(TimeZone.currentSystemDefault())
//            val daysDifference = currentDateTime.date.dayOfYear - savedDateTime.date.dayOfYear
//
//            daysDifference < 1
//        } else false
//    }

    override suspend fun saveCurrency(currency: CurrencyEntity) {
        realm.write {
            copyToRealm(currency)
        }
    }

    override fun getCurrencies(): Flow<List<CurrencyEntity>> {
        return realm.query<CurrencyEntity>()
            .asFlow()
            .map { it.list }
    }

    override suspend fun clearData() {
        realm.write {
            val currencies = query<CurrencyEntity>()
            delete(currencies)
        }
    }

    companion object {
        const val LAST_UPDATE = "last_update"
    }

}