package com.cdsap.challenge.domain.usecases

import com.cdsap.challenge.domain.entities.Result
import com.cdsap.challenge.domain.repository.PhotosRepository

class SearchPhotosImpl(private val repository: PhotosRepository) : SearchPhotos {
    private var page = 0
    override fun search(label: String, refresh: Boolean): Result {
        if (refresh) {
            page++
        } else {
            page = 1
        }
        return repository.searchPhotos(label, page)
    }
}
