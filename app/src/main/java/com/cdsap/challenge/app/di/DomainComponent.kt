package com.cdsap.challenge.app.di

import com.cdsap.challenge.domain.repository.PhotosRepository
import com.cdsap.challenge.domain.usecases.SearchPhotos
import com.cdsap.challenge.domain.usecases.SearchPhotosImpl
import com.cdsap.challenge.repository.PhotosRepositoryImpl

interface DomainComponent {
    val searchPhotos: SearchPhotos
    val repository: PhotosRepository
}

object DomainModule : DomainComponent {
    override val repository = PhotosRepositoryImpl(searchPhotosCall = RepositoryModule.searchPhotosCall, json = RepositoryModule.json)
    override val searchPhotos = SearchPhotosImpl(repository)
}