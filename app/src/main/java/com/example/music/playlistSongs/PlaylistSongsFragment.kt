package com.example.music.playlistSongs

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.domain.model.Music
import com.example.domain.model.Playlist
import com.example.music.R
import com.example.music.databinding.FragmentPlaylistSongsBinding
import com.example.music.homeFragment.MusicRecyclerAdapter

const val ARG_PLAYLIST = "arg_playlist"
const val ARG_NEW_ID = "arg_new_id"

class PlaylistSongsFragment : Fragment(), MusicRecyclerAdapter.OnMusicClickListener {

    private var _binding: FragmentPlaylistSongsBinding? = null
    private val binding get() = _binding!!
    private val playlistSongsViewModel: PlaylistSongsViewModel by activityViewModels()
    private var playlistName: String = ""
    private var playlistId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistSongsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val playlist: Playlist? = arguments?.getParcelable(ARG_PLAYLIST)
        val idForNewPlaylist: Int = arguments?.getInt(ARG_NEW_ID) ?: 0
        setAdapter()
        setEditTextListener()
        //setAddMusicButtonListener()
        if (playlist != null && playlist.playlistId != -1) {
            playlistName = playlist.name
            playlistId = playlist.playlistId
            playlistSongsViewModel.setPlaylistItem(playlist)
            binding.playlistName.text = Editable.Factory.getInstance().newEditable(playlistName)
            Glide.with(requireContext())
                .load(Uri.parse(playlist.artUri))
                .placeholder(R.drawable.icon_music_note)
                .into(binding.playlistImage)
        } else {
            binding.buttonSave.visibility = View.VISIBLE
            playlistId = -1
        }
        setSaveButtonListener(playlist, idForNewPlaylist)
    }

    private fun setSaveButtonListener(playlist: Playlist?, idForNewPlaylist: Int) {
        if (playlist != null) {
            binding.buttonSave.setOnClickListener {
                if (playlist.playlistId == -1) {
                    playlistSongsViewModel.addNewPlaylistItem(Playlist(idForNewPlaylist, playlistName, ""))
                } else {
                    playlistSongsViewModel.updatePlaylist(Playlist(playlist.playlistId, playlistName, playlist.artUri))
                }
            }
        }
    }

    private fun setEditTextListener() {
        binding.playlistName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.buttonSave.visibility = View.VISIBLE
            }

            override fun afterTextChanged(text: Editable?) {
                playlistName = text.toString()
            }
        })
    }

    private fun setAdapter() {
        val recyclerView = binding.playlistSongsRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = MusicRecyclerAdapter(requireContext(), emptyList(), this)
        val adapter = MusicRecyclerAdapter(requireContext(), emptyList(), this)
        recyclerView.adapter = adapter
        playlistSongsViewModel.musicLiveData.observe(viewLifecycleOwner) { musicList ->
            musicList?.let {
                adapter.updateData(it)
            }
        }
    }

    companion object {
        fun newInstance(playlist: Playlist, newId: Int): PlaylistSongsFragment =
            PlaylistSongsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PLAYLIST, playlist)
                    putInt(ARG_NEW_ID, newId)
                }
            }
    }

    override fun omMusicClickAction(music: Music) {
        TODO("Not yet implemented")
    }
}