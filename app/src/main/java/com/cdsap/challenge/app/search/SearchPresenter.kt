package com.cdsap.challenge.app.search

import com.cdsap.challenge.domain.entities.Result
import com.cdsap.challenge.domain.usecases.SearchPhotos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchPresenter(private val searchPhotos: SearchPhotos, private val view: SearchPhotosView) {

    suspend fun search(query: String, refresh: Boolean) {
        when (val result = withContext(Dispatchers.Default) { searchPhotos.search(query, refresh) }) {
            is Result.Error -> {
                view.showError(result.message)
            }
            is Result.ListPhoto -> {
                view.showView(SearchViewModelMapper().transform(result.list), refresh)
            }
        }


    }


}
