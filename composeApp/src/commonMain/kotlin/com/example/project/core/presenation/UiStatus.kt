package com.example.project.core.presenation

import com.example.project.core.domain.UiText

/**
 * created by Karim Haggagi on 12/22/24
 **/
sealed class UiStatus<out R> {
    data object Idle : UiStatus<Nothing>()
    data object Loading : UiStatus<Nothing>()
    data class Success<out T>(val data: T) : UiStatus<T>()
    data class Error(val error: UiText) : UiStatus<Nothing>()

}