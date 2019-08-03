package com.cdsap.challenge.app.search

import com.cdsap.challenge.app.photo.PhotoViewModel
import com.cdsap.challenge.domain.entities.Photo
import com.cdsap.challenge.domain.mapper.Mapper

class SearchViewModelMapper : Mapper<List<Photo>, List<PhotoViewModel>> {

    override fun transform(input: List<Photo>): List<PhotoViewModel> {
        return input.flatMap {
            listOf(
                    PhotoViewModel(
                            id = it.id,
                            title = it.title,
                            url = "https://farm${it.farm}.static.flickr.com/${it.server}/${it.id}_${it.secret}.jpg"
                    )
            )
        }.toList()
    }

}