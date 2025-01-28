package com.example.domain.model

data class PlaylistSongs(
    val playlistId: Int,
    val songId: String,
    val dataAdded: Long,
)