package com.cdsap.challenge.app.search

import com.cdsap.challenge.app.photo.PhotoViewModel

interface SearchPhotosView {
    fun showView(photos: List<PhotoViewModel>, refresh: Boolean)

    fun showError(message: String)
}