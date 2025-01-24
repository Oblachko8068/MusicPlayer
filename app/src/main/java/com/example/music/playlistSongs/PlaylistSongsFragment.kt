package com.example.music.playlistSongs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.Music
import com.example.domain.model.Playlist
import com.example.music.R
import com.example.music.databinding.FragmentPlaylistSongsBinding
import com.example.music.homeFragment.MusicRecyclerAdapter

const val ARG_PLAYLIST = "arg_playlist"

class PlaylistSongsFragment : Fragment(), MusicRecyclerAdapter.OnMusicClickListener{

    private var _binding: FragmentPlaylistSongsBinding? = null
    private val binding get() = _binding!!
    private val playlistSongsViewModel: PlaylistSongsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_playlist_songs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val playlist: Playlist? = arguments?.getParcelable(ARG_PLAYLIST)
        setAdapter()
    }

    private fun setAdapter() {
        val recyclerView = binding.playlistSongsRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = MusicRecyclerAdapter(requireContext(), emptyList(), this)
        val adapter = recyclerView.adapter as MusicRecyclerAdapter
        //val songsMediatorLiveData =
    }

    companion object{
        fun newInstance(playlist: Playlist): PlaylistSongsFragment =
            PlaylistSongsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PLAYLIST, playlist)
                }
            }
    }

    override fun omMusicClickAction(music: Music) {
        TODO("Not yet implemented")
    }
}