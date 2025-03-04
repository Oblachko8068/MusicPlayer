package com.example.music.playlistSongs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Music
import com.example.domain.model.Playlist
import com.example.domain.repository.MusicRepository
import com.example.domain.repository.PlaylistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistSongsViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
    private val playlistRepository: PlaylistRepository
) : ViewModel() {

    private val _musicLiveData = MutableLiveData<List<Music>>()
    val musicLiveData: LiveData<List<Music>> = _musicLiveData

    fun setPlaylistItem(playlist: Playlist) {
        viewModelScope.launch {
            try {
                val musicList = musicRepository.getMusicListFromPlaylist(playlist.playlistId)
                _musicLiveData.postValue(musicList.value)
            } catch (e: Exception) {
                _musicLiveData.postValue(emptyList())
            }
        }
    }

    fun updatePlaylist(playlist: Playlist) {
        viewModelScope.launch {
            playlistRepository.updatePlaylist(playlist)
        }
    }

    fun addNewPlaylistItem(playlist: Playlist) {
        viewModelScope.launch {
            playlistRepository.addNewPlaylist(playlist)
        }
    }
}
