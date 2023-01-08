package com.example.composenewsapp.roomDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.composenewsapp.dataModels.FeedItemData

@Dao
interface FeedDao {

    @Query("Select * from feed_items where category Like :category")
    fun getFeeds(category : String) : List<FeedItemData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFeeds(list : List<FeedItemData>)

    @Query("Delete from feed_items where category like :category")
    fun deleteFeed(category : String)
}