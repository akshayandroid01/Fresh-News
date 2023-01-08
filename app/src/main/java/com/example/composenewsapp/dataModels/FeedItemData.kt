package com.example.composenewsapp.dataModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feed_items")
data class FeedItemData(
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    var category : String? = null,
    var title : String?= null,
    var description : String? = null,
    var publish_date : String? = null,
    var image_link : String? = null
){

}
