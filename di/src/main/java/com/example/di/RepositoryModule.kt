package com.example.di

import com.example.data.repository.MusicFetchRepositoryImpl
import com.example.data.repository.MusicRepositoryImpl
import com.example.domain.repository.MusicFetchRepository
import com.example.domain.repository.MusicRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMusicFetchRepository(
        musicFetchRepository: MusicFetchRepositoryImpl
    ): MusicFetchRepository

    @Binds
    @Singleton
    abstract fun bindMusicRepository(
        musicRepository: MusicRepositoryImpl
    ): MusicRepository
}