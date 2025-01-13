package com.example.di

import com.example.data.repository.MusicFetchRepositoryImpl
import com.example.domain.repository.MusicFetchRepository
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
}