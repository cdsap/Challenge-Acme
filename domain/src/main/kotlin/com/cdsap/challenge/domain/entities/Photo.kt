package com.cdsap.challenge.domain.entities

data class Photo(
        val id: String,
        val owner: String,
        val title: String,
        val farm: String,
        val server: String,
        val secret: String
)
