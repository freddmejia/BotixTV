package com.example.botixtv.sealed

sealed class Resultado{
    data class Success<T>(val data: T): Resultado()
    data class Error(val error: String) : Resultado()
    object Empty : Resultado()
    sealed class HandleErrorNetwork{
        data class internalGraphqlError(val error: String): HandleErrorNetwork()
        data class serverGraphqlError(val error: String): HandleErrorNetwork()
    }
}
