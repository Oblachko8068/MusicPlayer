package com.example.data.repository

import android.content.Context
import com.example.domain.repository.SortingRepository
import javax.inject.Inject

class SortingRepositoryImpl @Inject constructor(
    private val appContext: Context
) : SortingRepository {

    override fun getSavedSorting(): List<Int> = getSharedPrefList()

    private fun getSharedPrefList(): List<Int> {
        val sharedPref = appContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return listOf(sharedPref.getInt("key_value1", 0), sharedPref.getInt("key_value2", 0))
    }

    override fun saveLastSorting(sorting: List<Int>) {
        saveToSharedPref(sorting)
    }

    private fun saveToSharedPref(list: List<Int>) {
        appContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit().apply {
            putInt("key_value1", list[0])
            putInt("key_value2", list[1])
            apply()
        }
    }
}