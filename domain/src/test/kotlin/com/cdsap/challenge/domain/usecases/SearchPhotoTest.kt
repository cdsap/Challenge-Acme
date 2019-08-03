package com.cdsap.challenge.domain.usecases

import com.cdsap.challenge.domain.entities.Photo
import com.cdsap.challenge.domain.entities.Result
import com.cdsap.challenge.domain.repository.PhotosRepository
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test

class SearchPhotoTest {

    @Test
    fun testSearchCallsRepositoryWithInitialPageNumber() {
        // Arrange
        val repository = mock<PhotosRepository>()
        val searchPhotos = SearchPhotosImpl(repository)

        // Act
        searchPhotos.search("dog", false)

        // Assert
        verify(repository).searchPhotos("dog", 1)
    }

    @Test
    fun testSearchCallsRepositoryMoreThanOnce() {
        // Arrange
        val repository = mock<PhotosRepository>()
        val searchPhotos = SearchPhotosImpl(repository)

        // Act
        searchPhotos.search("dog", false)
        searchPhotos.search("dog", true)
        searchPhotos.search("dog", true)
        searchPhotos.search("cat", false)

        // Assert
        inOrder(repository) {
            verify(repository).searchPhotos("dog", 1)
            verify(repository).searchPhotos("dog", 2)
            verify(repository).searchPhotos("dog", 3)
            verify(repository).searchPhotos("cat", 1)
        }
    }

    @Test
    fun testUseCaseReturnsListOfPhoto() {
        // Arrange
        val repository = mock<PhotosRepository>()
        whenever(repository.searchPhotos("dog", 1)).thenReturn(Result.ListPhoto(listOf(Photo(id = "1",
                owner = "owner",
                farm = "farm",
                server = "server",
                secret = "secret",
                title = "title"))))
        val searchPhotos = SearchPhotosImpl(repository)

        // Act
        val result = searchPhotos.search("dog", false)

        // Assert
        assert(result is Result.ListPhoto)
        val list = result as Result.ListPhoto
        assert(list.list.size == 1)
        assert(list.list[0].farm == "farm" &&
                list.list[0].owner == "owner" &&
                list.list[0].farm == "farm" &&
                list.list[0].server == "server" &&
                list.list[0].secret == "secret" &&
                list.list[0].title == "title")


    }

}
