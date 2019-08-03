package com.cdsap.challenge.repository.mapper

import com.cdsap.challenge.domain.entities.Photo
import com.cdsap.challenge.domain.entities.Result
import com.cdsap.challenge.domain.mapper.Mapper
import com.cdsap.challenge.repository.remote.entities.Network


class SearchMapper : Mapper<Network, Result> {

    override fun transform(input: Network): Result {
        return try {
            val a = input.photos.photo.flatMap {
                listOf(Photo(it.id, it.owner, it.title, it.farm, it.server, it.secret))
            }
            Result.ListPhoto(a)
        } catch (e: Exception) {
            Result.Error("Error mapping results from server")
        }
    }
}