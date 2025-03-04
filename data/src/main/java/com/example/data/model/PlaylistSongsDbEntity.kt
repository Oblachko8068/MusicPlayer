package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.PlaylistSongs

@Entity(
    tableName = "playlist_songs"
)

data class PlaylistSongsDbEntity(
    val playlistId: Int,
    @PrimaryKey val songId: String,
    val dataAdded: Long
) {
    fun toPlaylistSongsDb(): PlaylistSongs = PlaylistSongs(
        playlistId = playlistId,
        songId = songId,
        dataAdded = dataAdded
    )
}

fun PlaylistSongs.fromPlaylistSongsToPlaylistSongsDbEntity(): PlaylistSongsDbEntity =
    PlaylistSongsDbEntity(
        playlistId = this.playlistId,
        songId = this.songId,
        dataAdded = this.dataAdded
    )