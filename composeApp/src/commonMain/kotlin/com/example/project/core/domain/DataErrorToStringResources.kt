package com.example.project.core.domain

import currencyconverter.composeapp.generated.resources.Res
import currencyconverter.composeapp.generated.resources.*


/**
 * created by Karim Haggagi on 11/20/24
 **/

fun DataError.toUiText(): UiText.ResourceString {
   val resourceId = when(this){
        DataError.Local.DISK_FULL -> Res.string.data_error_local_disk_full
        DataError.Local.UNKNOWN -> Res.string.data_error_local_unknown
        DataError.Remote.REQUEST_TIMEOUT ->Res.string.data_error_remote_too_many_requests
        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.data_error_remote_too_many_requests
        DataError.Remote.NO_INTERNET -> Res.string.data_error_remote_no_internet
        DataError.Remote.SERVER -> Res.string.data_error_remote_server_error
        DataError.Remote.SERIALIZATION -> Res.string.data_error_remote_serialization_error
        DataError.Remote.UNKNOWN -> Res.string.data_error_remote_unknown_error
    }
    return UiText.ResourceString(resourceId)
}