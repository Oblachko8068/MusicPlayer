package com.example.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.MusicDbEntity

@Database(
    version = 1,
    entities = [
        MusicDbEntity::class
    ]
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun getMusicDao(): MusicDao
}