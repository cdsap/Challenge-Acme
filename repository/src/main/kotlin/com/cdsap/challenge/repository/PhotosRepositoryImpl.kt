package com.cdsap.challenge.repository


import com.cdsap.challenge.domain.entities.Result
import com.cdsap.challenge.domain.repository.PhotosRepository
import com.cdsap.challenge.repository.mapper.SearchMapper
import com.cdsap.challenge.repository.remote.HttpCall
import com.cdsap.challenge.repository.remote.entities.Network
import com.cdsap.challenge.repository.remote.entities.NetworkResult
import kotlinx.serialization.json.Json


class PhotosRepositoryImpl(private val searchPhotosCall: HttpCall,
                           private val json: Json) : PhotosRepository {

    override fun searchPhotos(value: String, page: Int): Result =
            when (val response = searchPhotosCall.get(value, page.toString())) {
                is NetworkResult.Error -> Result.Error(response.message)
                is NetworkResult.HttpResult.Response -> {
                    try {
                        SearchMapper().transform(json.parse(Network.serializer(), response.content))
                    } catch (e: Exception) {
                        Result.Error("error serializing")
                    }
                }
            }
}
