package com.cdsap.challenge.domain.usecases


import com.cdsap.challenge.domain.entities.Result

interface SearchPhotos {
    fun search(label: String, refresh: Boolean): Result
}