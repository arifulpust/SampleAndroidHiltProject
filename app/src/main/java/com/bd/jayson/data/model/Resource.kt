package com.bd.jayson.data.model



sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure<out T>(val error: Error) : Resource<T>()
}