package com.example.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.MusicDbEntity

@Dao
interface MusicDao {

    @Query("SELECT * FROM music")
    fun getAllMusicList(): LiveData<List<MusicDbEntity>>

    @Query("SELECT * FROM music WHERE playlistId = :playlistId")
    fun getMusicListByPlaylist(playlistId: Int): LiveData<List<MusicDbEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewMusic(musicDbEntity: MusicDbEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllMusicFromPhone(allMusic: List<MusicDbEntity>)
}