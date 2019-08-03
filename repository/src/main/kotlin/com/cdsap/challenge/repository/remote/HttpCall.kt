package com.cdsap.challenge.repository.remote

import com.cdsap.challenge.repository.remote.HttpCall.Constants.CONNECTION_TIMEOUT
import com.cdsap.challenge.repository.remote.HttpCall.Constants.READ_TIMEOUT
import com.cdsap.challenge.repository.remote.entities.NetworkResult
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

interface HttpCall {

    fun get(vararg args: String): NetworkResult

    fun request(url: String): NetworkResult {
        val conn = connection(url)
        return when (conn.responseCode) {
            HttpURLConnection.HTTP_OK -> {
                val result: StringBuilder? = result(conn)

                NetworkResult.HttpResult.Response(result!!.toString())
            }
            HttpURLConnection.HTTP_MOVED_PERM -> {
                return try {
                    val redirectUrl = conn.getHeaderField("Location")
                    NetworkResult.HttpResult.Response(redirectUrl)
                } catch (e: Exception) {
                    println("${e.message}")
                    NetworkResult.Error("error requesting Bitmap")
                }
            }
            else -> return NetworkResult.Error("error requesting Url")
        }
    }

    private fun result(conn: HttpURLConnection): StringBuilder? {
        val result: StringBuilder? = java.lang.StringBuilder()
        val inputStream = conn.inputStream
        val inReader = InputStreamReader(inputStream)
        inReader.buffered().lines().forEach {
            result!!.append(it)
        }
        inReader.close()
        inputStream.close()
        return result
    }

    private fun connection(url: String): HttpURLConnection {
        val urlAddress = URL(url)
        val conn = urlAddress.openConnection() as HttpURLConnection
        conn.apply {
            connectTimeout = CONNECTION_TIMEOUT
            readTimeout = READ_TIMEOUT
            requestMethod = "GET"
            connect()
        }
        return conn
    }

    object Constants {
        const val CONNECTION_TIMEOUT = 30 * 1000
        const val READ_TIMEOUT = 30 * 1000
    }
}
