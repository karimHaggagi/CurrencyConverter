package com.example.project.core.domain

/**
 * created by Karim Haggagi on 12/15/24
 **/

sealed interface Result<out T, out E : Error> {
    data class Success<out T>(val data: T) : Result<T, Nothing>
    data class Error<out E : com.example.project.core.domain.Error>(val error: E) :
        Result<Nothing, E>

}

inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Success -> Result.Success(map(data))
        is Result.Error -> Result.Error(error)
    }
}

inline fun <T, E : Error, R> Result<T, E>.fold(onSuccess: (T) -> R, onError: (E) -> R): R {
    return when (this) {
        is Result.Success -> onSuccess(data)
        is Result.Error -> onError(error)
    }
}

inline fun <T, E : Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Success -> {
            action(data)
            this
        }

        is Result.Error -> this
    }
}

inline fun <T, E : Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Success -> this
        is Result.Error -> {
            action(error)
            this
        }
    }
}

typealias EmptyResult = Result<Unit, Error>