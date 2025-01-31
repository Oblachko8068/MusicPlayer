package com.example.music.playlistSongs

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.domain.model.Music
import com.example.domain.model.Playlist
import com.example.music.R
import com.example.music.databinding.FragmentPlaylistSongsBinding
import com.example.music.musicListFragment.MusicRecyclerAdapter
import com.example.music.optionDialogs.OPT_DIALOG_RES
import com.example.music.optionDialogs.OPT_DIALOG_TAG
import com.example.music.optionDialogs.OptionDialogFragment

const val ARG_PLAYLIST = "arg_playlist"
const val ARG_NEW_ID = "arg_new_id"

class PlaylistSongsFragment : Fragment(), MusicRecyclerAdapter.OnMusicClickListener {

    private var _binding: FragmentPlaylistSongsBinding? = null
    private val binding get() = _binding!!
    private val playlistSongsViewModel: PlaylistSongsViewModel by activityViewModels()
    private var newPlaylistName: String = ""

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
        //setAddMusicButtonListener()
        if (playlist != null && playlist.playlistId != -1) {
            playlistSongsViewModel.setPlaylistItem(playlist)
            binding.playlistName.text = Editable.Factory.getInstance().newEditable(playlist.name)
            Glide.with(requireContext())
                .load(Uri.parse(playlist.artUri))
                .placeholder(R.drawable.icon_music_note)
                .into(binding.playlistImage)
        } else {
            binding.buttonSave.visibility = View.VISIBLE
        }
        setSaveButtonListener(playlist, idForNewPlaylist)
        setEditTextListener()
        setOptionButtonListener()
    }

    private fun setOptionButtonListener() {
        val optionsList = resources.getStringArray(R.array.options_playlist_songs_fragment)
        binding.buttonOptions.setOnClickListener {
            OptionDialogFragment.newInstance(optionsList).show(parentFragmentManager, OPT_DIALOG_TAG)
        }
        parentFragmentManager.setFragmentResultListener(OPT_DIALOG_RES, viewLifecycleOwner) { _, res ->

        }
    }

    private fun setSaveButtonListener(playlist: Playlist?, idForNewPlaylist: Int) {
        if (playlist != null) {
            binding.buttonSave.setOnClickListener {
                if (newPlaylistName != ""){
                    if (playlist.playlistId == -1) {
                        playlistSongsViewModel.addNewPlaylistItem(Playlist(idForNewPlaylist, newPlaylistName, ""))
                    } else {
                        playlistSongsViewModel.updatePlaylist(Playlist(playlist.playlistId, newPlaylistName, playlist.artUri))
                    }
                } else {
                    Toast.makeText(requireContext(), "Введите название", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setEditTextListener() {
        binding.playlistName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                newPlaylistName = text.toString()
                binding.buttonSave.visibility = View.VISIBLE
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