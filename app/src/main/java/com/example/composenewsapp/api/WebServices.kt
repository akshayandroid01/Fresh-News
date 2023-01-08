package com.example.composenewsapp.api

import com.example.composenewsapp.dataModels.FeedsResponse

interface WebServices {

    suspend fun getFeeds(endPoint : String) : FeedsResponse?
}