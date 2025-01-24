package com.example.di

import com.example.data.repository.MusicFetchRepositoryImpl
import com.example.data.repository.MusicRepositoryImpl
import com.example.data.repository.PlaylistRepositoryImpl
import com.example.data.repository.PlaylistSongsRepositoryImpl
import com.example.data.repository.SortingRepositoryImpl
import com.example.domain.repository.MusicFetchRepository
import com.example.domain.repository.MusicRepository
import com.example.domain.repository.PlaylistRepository
import com.example.domain.repository.PlaylistSongsRepository
import com.example.domain.repository.SortingRepository
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

    @Binds
    @Singleton
    abstract fun bindPlaylistRepository(
        playlistRepository: PlaylistRepositoryImpl
    ): PlaylistRepository

    @Binds
    @Singleton
    abstract fun bindPlaylistSongsRepository(
        playlistSongs: PlaylistSongsRepositoryImpl
    ): PlaylistSongsRepository

    @Binds
    @Singleton
    abstract fun binSortingRepository(
        sortingRepository: SortingRepositoryImpl
    ): SortingRepository
}