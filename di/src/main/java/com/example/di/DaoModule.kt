package com.example.di

import android.content.Context
import androidx.room.Room
import com.example.data.room.AppDatabase
import com.example.data.room.MusicDao
import com.example.data.room.PlaylistDao
import com.example.data.room.PlaylistSongsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(appContext, AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideMusicDao(appDatabase: AppDatabase): MusicDao = appDatabase.getMusicDao()

    @Provides
    @Singleton
    fun providePlaylistDao(appDatabase: AppDatabase): PlaylistDao = appDatabase.getPlaylistDao()

    @Provides
    @Singleton
    fun providePlaylistSongsDao(appDatabase: AppDatabase): PlaylistSongsDao =
        appDatabase.getPlaylistSongsDao()
}