package com.example.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.MusicDbEntity
import com.example.data.model.PlaylistDbEntity
import com.example.data.model.PlaylistSongsDbEntity

@Database(
    version = 3,
    entities = [
        MusicDbEntity::class,
        PlaylistDbEntity::class,
        PlaylistSongsDbEntity::class
    ],
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun getMusicDao(): MusicDao

    abstract fun getPlaylistDao(): PlaylistDao

    abstract fun getPlaylistSongsDao(): PlaylistSongsDao
}