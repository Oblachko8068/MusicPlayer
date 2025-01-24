package com.example.music.playlistSongs

import androidx.lifecycle.ViewModel
import com.example.domain.repository.PlaylistSongsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistSongsViewModel @Inject constructor(
    private val playlistSongsRepository: PlaylistSongsRepository,
) : ViewModel() {

}