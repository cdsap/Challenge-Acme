package com.cdsap.challenge.repository.remote.entities


sealed class NetworkResult {

    sealed class HttpResult : NetworkResult() {
        data class Response(val content: String) : HttpResult()
    }

    data class Error(val message: String) : NetworkResult()
}