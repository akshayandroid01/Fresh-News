package com.example.composenewsapp.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composenewsapp.dataModels.FeedItemData
import com.example.composenewsapp.utills.AppConstants

@Database(entities = [FeedItemData::class],
    version = AppConstants.Current_Database_Version)
abstract class AppDatabase : RoomDatabase() {
    abstract fun feedDao(): FeedDao
}