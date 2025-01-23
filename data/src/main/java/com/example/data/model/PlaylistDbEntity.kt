package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Playlist

@Entity(
    tableName = "playlist"
)

data class PlaylistDbEntity(
    @PrimaryKey val playlistId: Int,
    val name: String
) {
    fun toPlaylistDb(): Playlist = Playlist(
        playlistId = playlistId,
        name = name
    )
}

fun Playlist.fromPlaylistToPlaylistDbEntity(): PlaylistDbEntity = PlaylistDbEntity(
    playlistId = this.playlistId,
    name = this.name
)