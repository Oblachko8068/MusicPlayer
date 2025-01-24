package com.example.data.repository

import android.content.Context
import com.example.domain.repository.SortingRepository
import javax.inject.Inject

const val SORT_SHRED_PREF = "sortSharedPref"
const val MSKV1 = "music_sort_key_value1"
const val MSKV2 = "music_sort_key_value2"
const val PSKV1 = "pl_sort_key_value1"
const val PSKV2 = "pl_sort_key_value2"

class SortingRepositoryImpl @Inject constructor(
    private val appContext: Context
) : SortingRepository {

    override fun getSavedSorting(isMusicSorting: Boolean): List<Int> =
        getSharedPrefList(isMusicSorting)

    override fun saveLastSorting(sorting: List<Int>, isMusicSorting: Boolean) {
        saveToSharedPref(sorting, isMusicSorting)
    }

    private fun getSharedPrefList(isMusicSorting: Boolean): List<Int> {
        val sharedPref = appContext.getSharedPreferences(SORT_SHRED_PREF, Context.MODE_PRIVATE)
        return listOf(
            sharedPref.getInt(if (isMusicSorting) MSKV1 else PSKV1, 0),
            sharedPref.getInt(if (isMusicSorting) MSKV2 else PSKV2, 0)
        )
    }

    private fun saveToSharedPref(list: List<Int>, isMusicSorting: Boolean) {
        appContext.getSharedPreferences(SORT_SHRED_PREF, Context.MODE_PRIVATE).edit().apply {
            if (isMusicSorting) {
                putInt(MSKV1, list[0])
                putInt(MSKV2, list[1])
            } else {
                putInt(PSKV1, list[0])
                putInt(PSKV2, list[1])
            }
            apply()
        }
    }
}