package com.example.music.playlistFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.Playlist
import com.example.domain.repository.PlaylistRepository
import com.example.domain.repository.SortingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val playlistRepository: PlaylistRepository,
    private val coroutineContext: CoroutineContext,
    private val sortingRepository: SortingRepository
) : ViewModel() {

    private var playlistLiveData: LiveData<List<Playlist>> = playlistRepository.getPlaylistList()
    private var mediatorLiveData = MediatorLiveData<List<Playlist>>()
    private var currentSorting: List<Int> = sortingRepository.getSavedSorting(false)

    init {
        mediatorLiveData.addSource(playlistLiveData) {
            mediatorLiveData.value = it
        }
    }

    fun getPlaylistMediatorLiveData(): MediatorLiveData<List<Playlist>> = mediatorLiveData

    private fun getPlaylistList(): List<Playlist> = playlistLiveData.value ?: emptyList()

    fun getCurrentSorting(): List<Int> = currentSorting

    fun sortMusicList(newSorting: List<Int>) {
        val isReverseSorting = newSorting[1]
        var sortingList = when (newSorting[0]) {
            0 -> getPlaylistList().sortedBy { it.name }
            1 -> getPlaylistList().sortedBy { it.playlistId }
            else -> getPlaylistList().sortedBy { it.playlistId }
        }
        if (isReverseSorting == 1)
            sortingList = sortingList.reversed()
        mediatorLiveData.value = sortingList
        currentSorting = newSorting
    }

    fun saveCurrentSorting() {
        sortingRepository.saveLastSorting(currentSorting, isMusicSorting = false)
    }
}