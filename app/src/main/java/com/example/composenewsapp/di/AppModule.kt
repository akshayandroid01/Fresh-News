package com.example.composenewsapp.di

import android.content.Context
import androidx.room.Room
import com.example.composenewsapp.api.WebServices
import com.example.composenewsapp.api.ktorHttpClient
import com.example.composenewsapp.repository.FeedsRepositoryImp
import com.example.composenewsapp.roomDatabase.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideWebServices() : WebServices = FeedsRepositoryImp(ktorHttpClient)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context) : AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "news_app _database"
        ).build()
    }
}