package com.cdsap.challenge.app.search

import com.cdsap.challenge.app.photo.PhotoViewModel
import com.cdsap.challenge.domain.entities.Photo
import com.cdsap.challenge.domain.entities.Result
import com.cdsap.challenge.domain.usecases.SearchPhotos
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SearchPresenterTest {

    @Test
    fun testPresenterShowErrorWhenUseCaseReturnsError() = runBlocking {
        // Arrange
        val searchPhotos = mock<SearchPhotos>()
        whenever(searchPhotos.search("dog", false)).thenReturn(Result.Error("Error"))
        val view = mock<SearchPhotosView>()
        val presenter = SearchPresenter(searchPhotos, view)

        // Act
        presenter.search("dog", false)

        // Assert
        verify(view).showError("Error")
    }

    @Test
    fun testPresenterShowViewList() = runBlocking {
        // Arrange
        val searchPhotos = mock<SearchPhotos>()
        whenever(searchPhotos.search("dog", false)).thenReturn(Result.ListPhoto(listOf(Photo(id = "1",
                farm = "farm",
                title = "title",
                secret = "secret",
                server = "server",
                owner = "owner"
        ))))
        val view = mock<SearchPhotosView>()
        val presenter = SearchPresenter(searchPhotos, view)

        // Act
        presenter.search("dog", false)

        // Assert
        verify(view).showView(listOf(PhotoViewModel(id = "1",
                title = "title",
                url = "https://farmfarm.static.flickr.com/server/1_secret.jpg")), false)
    }

}