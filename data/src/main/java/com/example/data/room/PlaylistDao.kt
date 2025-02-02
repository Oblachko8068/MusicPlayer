package com.example.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.PlaylistDbEntity

@Dao
interface PlaylistDao {

    @Query("SELECT * FROM playlist")
    fun getAllPlaylistList(): LiveData<List<PlaylistDbEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewPlaylist(playlistDbEntity: PlaylistDbEntity)

    @Update
    suspend fun updatePlaylist(playlistDbEntity: PlaylistDbEntity)
}