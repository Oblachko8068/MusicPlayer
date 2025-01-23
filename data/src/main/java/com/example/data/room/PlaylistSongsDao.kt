package com.example.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.PlaylistSongsDbEntity

@Dao
interface PlaylistSongsDao {

    @Query("SELECT * FROM playlist_songs WHERE playlistId = :playlistId")
    fun getAllSongsFromPlaylist(playlistId: Int): LiveData<List<PlaylistSongsDbEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewSongToPlaylist(playlistSongsDbEntity: PlaylistSongsDbEntity)
}