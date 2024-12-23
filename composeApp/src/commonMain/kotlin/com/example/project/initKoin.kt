package com.example.project

import com.example.project.di.platformModule
import com.example.project.di.sharedModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

/**
 * created by Karim Haggagi on 12/15/24
 **/

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule, platformModule)
    }
}