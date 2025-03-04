package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Playlist

@Entity(
    tableName = "playlist"
)

data class PlaylistDbEntity(
    @PrimaryKey val playlistId: Int,
    val name: String,
    val artUri: String
) {
    fun toPlaylistDb(): Playlist = Playlist(
        playlistId = playlistId,
        name = name,
        artUri = artUri
    )
}

fun Playlist.fromPlaylistToPlaylistDbEntity(): PlaylistDbEntity = PlaylistDbEntity(
    playlistId = this.playlistId,
    name = this.name,
    artUri = this.artUri
)