package com.cdsap.challenge.domain.repository

import com.cdsap.challenge.domain.entities.Result

interface PhotosRepository {

    fun searchPhotos(value: String, page: Int): Result
}