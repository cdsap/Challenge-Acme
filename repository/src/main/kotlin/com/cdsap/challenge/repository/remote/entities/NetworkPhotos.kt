package com.cdsap.challenge.repository.remote.entities

import kotlinx.serialization.Serializable

@Serializable
data class Network(val photos: NetworkPhotos)


@Serializable
data class NetworkPhotos(val photo: List<NetworkPhoto>)

@Serializable
data class NetworkPhoto(
        val id: String,
        val owner: String,
        val title: String,
        val farm: String,
        val server: String,
        val secret: String
)