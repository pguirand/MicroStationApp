package com.example.microstationapp.data.remote

import java.lang.Exception

sealed class ApiResponse<out T : Any> {
    data class Success<out T : Any>(val data : T) : ApiResponse<T>()
    data class Error(val exception: String) : ApiResponse<Nothing>()
    data object Loading : ApiResponse<Nothing>()
}