package com.cdsap.challenge.app.di

import com.cdsap.challenge.repository.remote.GetPhotosCall
import com.cdsap.challenge.repository.remote.HttpCall
import com.cdsap.challenge.repository.remote.SearchPhotosCall
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

interface RepositoryComponent {
    val json: Json
    val searchPhotosCall: HttpCall
    val photosCall: HttpCall
}

object RepositoryModule : RepositoryComponent {
    override val json = Json(JsonConfiguration(
            encodeDefaults = true,
            strictMode = false,
            unquoted = false,
            prettyPrint = false,
            useArrayPolymorphism = false))

    override val searchPhotosCall = SearchPhotosCall()

    override val photosCall = GetPhotosCall()


}