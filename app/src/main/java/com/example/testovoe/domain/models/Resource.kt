package com.example.testovoe.domain.models

sealed interface Resource<T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Failed<T>(val message: FailureType) : Resource<T>
}

sealed class FailureType {
    object NoInternet : FailureType()
    object ApiError : FailureType()
}
