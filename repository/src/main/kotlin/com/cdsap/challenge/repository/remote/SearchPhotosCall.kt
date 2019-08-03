package com.cdsap.challenge.repository.remote

import com.cdsap.challenge.repository.remote.SearchPhotosCall.Constants.ENDPOINT
import com.cdsap.challenge.repository.remote.entities.NetworkResult

class SearchPhotosCall : HttpCall {
    override fun get(vararg args: String): NetworkResult {
        val id = "&text=${args[0]}"
        val page = "&page=${args[1]}"
        return request("$ENDPOINT$id$page")
    }


    object Constants {
        private const val KEY = "3e7cc266ae2b0e0d78e279ce8e361736"
        private const val SERVICE = "?method=flickr.photos.search&api_key=$KEY&format=json&per_page=20&nojsoncallback=1&safe_search=1"
        private const val URL = "https://api.flickr.com/services/rest"
        const val ENDPOINT = "$URL/$SERVICE"
    }
}