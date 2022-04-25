package com.example.meiyong.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error<out T : Any>(val IOException: Any?) : Result<Nothing>()

    override fun toString(): String {
//        return when (this) {
            return "Success"
////            is Error -> "Error[exception=$exception]"
//            else -> "Success[data=$data]"
//        }
    }
}