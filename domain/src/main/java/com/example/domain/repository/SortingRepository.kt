package com.example.domain.repository

interface SortingRepository {

    fun getSavedSorting(isMusicSorting: Boolean): List<Int>

    fun saveLastSorting(sorting: List<Int>, isMusicSorting: Boolean)
}