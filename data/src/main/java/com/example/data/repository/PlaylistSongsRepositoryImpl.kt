package com.example.data.repository

import com.example.data.room.PlaylistSongsDao
import com.example.domain.model.PlaylistSongs
import com.example.domain.repository.PlaylistSongsRepository
import javax.inject.Inject

class PlaylistSongsRepositoryImpl @Inject constructor(
    private val playlistSongsDao: PlaylistSongsDao
) : PlaylistSongsRepository {

    override fun getAllSongsPlaylist(): List<PlaylistSongs> =
        playlistSongsDao.getSongsPlaylist()
            .map { playlistSongsDbEntities -> playlistSongsDbEntities.toPlaylistSongsDb() }
}