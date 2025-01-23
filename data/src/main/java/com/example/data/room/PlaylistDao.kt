package com.example.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.PlaylistDbEntity

@Dao
interface PlaylistDao {

    @Query("SELECT * FROM playlist")
    fun getAllPlaylistList(): LiveData<List<PlaylistDbEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewPlaylist(playlistDbEntity: PlaylistDbEntity)
}