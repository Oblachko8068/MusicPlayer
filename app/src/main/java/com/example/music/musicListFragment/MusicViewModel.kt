package com.example.music.musicListFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Music
import com.example.domain.repository.MusicFetchRepository
import com.example.domain.repository.MusicRepository
import com.example.domain.repository.SortingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MusicViewModel @Inject constructor(
    musicRepository: MusicRepository,
    private val musicFetchRepository: MusicFetchRepository,
    private val sortingRepository: SortingRepository,
    coroutineContext: CoroutineContext
) : ViewModel() {

    private var musicLiveData: LiveData<List<Music>> = musicRepository.getMusicList()
    private var mediatorLiveData = MediatorLiveData<List<Music>>()
    private var currentSorting: List<Int> = sortingRepository.getSavedSorting(isMusicSorting = true)

    init {
        viewModelScope.launch(coroutineContext) {
            musicFetchRepository.fetchMusic()
        }
        mediatorLiveData.addSource(musicLiveData) {
            mediatorLiveData.value = it
        }
    }

    fun getMusicMediatorLiveData(): MediatorLiveData<List<Music>> = mediatorLiveData

    fun getCurrentSorting(): List<Int> = currentSorting

    private fun getMusicList(): List<Music> = musicLiveData.value ?: emptyList()

    fun getFilteredListMusic(): List<Music> {
        val music = getMusicList()
        val searchQuery = _searchTextLiveData.value
        return if (!searchQuery.isNullOrEmpty()) {
            music.filter {
                it.title.contains(searchQuery, ignoreCase = true)
            }
        } else {
            music
        }
    }

    fun sortMusicList(newSorting: List<Int>) {
        val isReverseSorting = newSorting[1]
        var sortingList = when (newSorting[0]) {
            0 -> getMusicList().sortedBy { it.title }
            1 -> getMusicList().sortedBy { it.duration }
            2 -> getMusicList().sortedBy { it.data }
            else -> getMusicList().sortedBy { it.data }
        }
        if (isReverseSorting == 1)
            sortingList = sortingList.reversed()
        mediatorLiveData.value = sortingList
        currentSorting = newSorting
    }

    fun saveCurrentSorting() {
        sortingRepository.saveLastSorting(currentSorting, isMusicSorting = true)
    }

    companion object {
        private val _searchTextLiveData = MutableLiveData<String>()
        val searchTextLiveData: LiveData<String> = _searchTextLiveData
        fun setSearchText(searchText: String) {
            _searchTextLiveData.value = searchText
        }
    }
}