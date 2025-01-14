package com.example.music

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Music
import com.example.domain.repository.MusicFetchRepository
import com.example.domain.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
    private val musicFetchRepository: MusicFetchRepository,
    coroutineContext: CoroutineContext
) : ViewModel(){

    private var musicLiveData: LiveData<List<Music>> = musicRepository.getMusicList()
    private var mediatorLiveData = MediatorLiveData<List<Music>>()

    init {
        viewModelScope.launch(coroutineContext) {
            musicFetchRepository.fetchMusic()
        }
        mediatorLiveData.addSource(musicLiveData) {
            mediatorLiveData.value = it
        }
    }

    fun getMusicMediatorLiveData(): MediatorLiveData<List<Music>> = mediatorLiveData

}