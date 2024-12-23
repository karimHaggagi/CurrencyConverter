package com.example.project.di

import com.example.project.core.data.local.RealmFactory
import com.example.project.core.data.network.HttpClientFactory
import com.example.project.currency.data.local.LocalDataSource
import com.example.project.currency.data.local.LocalPreferencesDataSource
import com.example.project.currency.data.network.KtorRemoteDataSource
import com.example.project.currency.data.network.RemoteDataSource
import com.example.project.currency.data.repoImpl.CurrencyRepoImpl
import com.example.project.currency.domain.repository.CurrencyRepository
import com.example.project.currency.domain.usecase.CalculateExchangeRateUseCase
import com.example.project.currency.domain.usecase.GetAllCurrenciesUseCase
import com.example.project.currency.presentation.currencyScreen.CurrencyViewModel
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * created by Karim Haggagi on 12/15/24
 **/
expect val platformModule: Module

val sharedModule = module {

    single { HttpClientFactory.create(get()) }
    single { RealmFactory.create() }
    single { Settings() }

    singleOf(::GetAllCurrenciesUseCase)
    singleOf(::KtorRemoteDataSource).bind<RemoteDataSource>()
    singleOf(::CurrencyRepoImpl).bind<CurrencyRepository>()
    singleOf(::LocalPreferencesDataSource).bind<LocalDataSource>()
    singleOf(::CalculateExchangeRateUseCase)
    viewModelOf(::CurrencyViewModel)
}