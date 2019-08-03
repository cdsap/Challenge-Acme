package com.cdsap.challenge.repository

import com.cdsap.challenge.domain.entities.Result
import com.cdsap.challenge.repository.remote.HttpCall
import com.cdsap.challenge.repository.remote.entities.NetworkResult
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.junit.Test

class PhotosRepositoryTest {
    private val json = json()

    @Test
    fun testGettingSearchParseNetworkErrorToResultError() {
        // Arrange
        val searchCall = mock<HttpCall>()

        whenever(searchCall.get("dog", "1")).thenReturn(NetworkResult.Error("error when requesting url"))
        val repository = PhotosRepositoryImpl(searchCall, json)

        // Act
        val result = repository.searchPhotos("dog", 1)

        // Assert
        assert(result is Result.Error)
        assert((result as Result.Error).message == "error when requesting url")
    }

    @Test
    fun testGettingSearchParseNetworkResultToResult() {
        // Arrange
        val searchCall = mock<HttpCall>()

        whenever(searchCall.get("dog", "1")).thenReturn(NetworkResult.HttpResult.Response("{PHOYO}"))
        val repository = PhotosRepositoryImpl(searchCall, json)

        // Act
        val result = repository.searchPhotos("dog", 1)

        // Assert
        assert(result is Result.Error)
        assert((result as Result.Error).message == "error serializing")
    }

    @Test
    fun testGettingSearchParseNetworkResultButErrorSerializing() {
        // Arrange
        val searchCall = mock<HttpCall>()

        whenever(searchCall.get("dog", "1")).thenReturn(NetworkResult.HttpResult.Response(jsonMock()))
        val repository = PhotosRepositoryImpl(searchCall, json)

        // Act
        val result = repository.searchPhotos("dog", 1)

        // Assert
        assert(result is Result.ListPhoto)
        assert((result as Result.ListPhoto).list.size == 1)
        assert(result.list[0].farm == "1" &&
                result.list[0].secret == "8983a8ebc7" &&
                result.list[0].id == "23451156376")
    }

    private fun json(): Json = Json(JsonConfiguration(
            encodeDefaults = true,
            strictMode = false,
            unquoted = false,
            prettyPrint = false,
            useArrayPolymorphism = false))

    private fun jsonMock() = """
{"photos":{"page":1,"pages":3363,"perpage":100,"total":"336259","photo": [
{
  "id": "23451156376",
  "owner": "28017113@N08",
  "secret": "8983a8ebc7",
  "server": "578",
  "farm": 1,
  "title": "Merry Christmas!",
  "ispublic": 1,
  "isfriend": 0,
  "isfamily": 0
}]}}
    """.trimIndent()

}
