package com.example.project.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * created by Karim Haggagi on 12/15/24
 **/
actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
    }
