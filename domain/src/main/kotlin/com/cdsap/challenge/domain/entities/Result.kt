package com.cdsap.challenge.domain.entities


sealed class Result {

    data class ListPhoto(val list: List<Photo>) : Result()

    data class Error(val message: String) : Result()
}

