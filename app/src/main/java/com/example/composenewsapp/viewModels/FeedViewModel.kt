package com.example.composenewsapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composenewsapp.api.UrlConstant
import com.example.composenewsapp.api.WebServices
import com.example.composenewsapp.dataModels.FeedItemData
import com.example.composenewsapp.dataModels.FeedsResponse
import com.example.composenewsapp.dataModels.ItemsItem
import com.example.composenewsapp.roomDatabase.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val webServices: WebServices,
    private val appDatabase: AppDatabase,
) : ViewModel() {

    private val _feeds = MutableLiveData<FeedsResponse?>()
    val feeds: LiveData<FeedsResponse?> = _feeds

    private val _feed = MutableLiveData<ItemsItem?>()
    val feed: LiveData<ItemsItem?> = _feed

    fun getFeeds(endPoint: String) {

        viewModelScope.launch {
            try {
                //if internet available, make server call
                    val response = webServices.getFeeds(getUrl(endPoint))
                    //if feed items found from server, update UI and save in local
                    if (response != null && !response.items.isNullOrEmpty()) {
                        _feeds.value = response
                        //save in DB
                        saveFeedInDatabase(response, endPoint)
                    } else {
                        //get data from local for endpoint
                        getDataInLocal(endPoint)
                    }

            } catch (e: Exception) {
                e.printStackTrace()
                _feeds.postValue(getErrorResponseFromException(e))
            }
        }
    }

    fun getDataInLocal(endPoint: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val response = FeedsResponse()
                    //if found null , check in local
                    val feeds =
                        appDatabase.feedDao().getFeeds(endPoint.replace("-", "").lowercase())
                    if (feeds.isEmpty()) {
                        response.status = "error"
                        response.message = "No Data Found!"
                    } else {
                        val itemList = ArrayList<ItemsItem>()
                        feeds.forEach {
                            val item = ItemsItem()
                            item.title = it.title
                            item.pubDate = it.publish_date
                            item.description = it.description
                            item.enclosure?.link = it.image_link

                            itemList.add(item)
                        }
                        response.status = "ok"
                        response.message = "Success"
                        response.items = itemList
                    }
                    _feeds.postValue(response)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _feeds.postValue(getErrorResponseFromException(e))
            }
        }
    }

    private fun getErrorResponseFromException(e: Exception): FeedsResponse {
        val model = FeedsResponse()
        model.status = "error"
        model.message = e.localizedMessage

        return model
    }

    private fun getFeedItemsDataInstance(item: ItemsItem, endPoint: String): FeedItemData {
        val model = FeedItemData()
        model.category = endPoint
        model.title = item.title
        model.description = item.description
        model.publish_date = item.pubDate
        model.image_link = item.enclosure?.link

        return model
    }

    private fun saveFeedInDatabase(feedsResponse: FeedsResponse, endPoint: String) {
        val feedList = ArrayList<FeedItemData>()
        feedsResponse.items?.forEach {
            feedList.add(getFeedItemsDataInstance(it, endPoint.replace("-", "").lowercase()))
        }

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                appDatabase.feedDao().deleteFeed(endPoint.replace("-", "").lowercase())
                appDatabase.feedDao().insertFeeds(feedList)
            }
        }
    }

    private fun getUrl(endPoint: String?): String {
        return when (endPoint) {
            "Education" -> UrlConstant.Education
            "Astrology" -> UrlConstant.Astrology
            "Books" -> UrlConstant.Books
            "Business" -> UrlConstant.Business
            "Car Bike" -> UrlConstant.CarBike
            "Cities" -> UrlConstant.Cities
            "Art-Culture" -> UrlConstant.ArtCulture
            "Lifestyle" -> UrlConstant.Lifestyle
            "Elections" -> UrlConstant.Elections
            else -> ""
        }
    }

    fun selectedItem(item: ItemsItem?) {
        _feed.value = item
    }
}