package com.example.composenewsapp.repository

import com.example.composenewsapp.api.WebServices
import com.example.composenewsapp.dataModels.FeedsResponse
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*

class FeedsRepositoryImp(private val client: HttpClient) : WebServices {

    override suspend fun getFeeds(endPoint: String): FeedsResponse? {
        return try {
            client.get(endPoint)
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: ServerResponseException) {
            // 5xx - response
            println("Error: ${ex.response.status.description}")
            null
        }
    }
}