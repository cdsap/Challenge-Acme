package com.cdsap.challenge.repository.remote

import com.cdsap.challenge.repository.remote.entities.NetworkResult

class GetPhotosCall : HttpCall {

    override fun get(vararg args: String): NetworkResult {
        return request(args[0])
    }
}