package com.example.domain.repository

interface MusicFetchRepository {

    suspend fun fetchMusic()
}