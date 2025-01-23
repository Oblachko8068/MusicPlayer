package com.example.music

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.Playlist
import com.example.domain.repository.PlaylistRepository
import com.example.domain.repository.PlaylistSongsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val playlistRepository: PlaylistRepository,
    private val playlistSongsRepository: PlaylistSongsRepository,
    private val coroutineContext: CoroutineContext
) : ViewModel() {

    private var playlistLiveData: LiveData<List<Playlist>> = playlistRepository.getPlaylistList()
    private var mediatorLiveData = MediatorLiveData<List<Playlist>>()

    init {
        mediatorLiveData.addSource(playlistLiveData) {
            mediatorLiveData.value = it
        }
    }

    fun getPlaylistMediatorLiveData(): MediatorLiveData<List<Playlist>> = mediatorLiveData
}