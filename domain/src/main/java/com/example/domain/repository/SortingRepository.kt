package com.example.domain.repository

interface SortingRepository {

    fun getSavedSorting(): List<Int>

    fun saveLastSorting(sorting: List<Int>)
}