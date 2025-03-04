package com.example.music.playlistFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.model.Playlist
import com.example.music.R
import com.example.music.databinding.FragmentPlaylistsBinding
import com.example.music.fragment.SettingsFragment
import com.example.music.playlistSongs.PlaylistSongsFragment
import com.example.music.sorting.NEW_SORT_NAME_PL
import com.example.music.sorting.NEW_SORT_TYPE_PL
import com.example.music.sorting.SORT_DIALOG_RES_PL
import com.example.music.sorting.SORT_DIALOG_TAG_PL
import com.example.music.sorting.SortDialogFragment

class PlaylistsFragment : Fragment(), PlaylistRecyclerAdapter.OnPlaylistClickListener {

    private var _binding: FragmentPlaylistsBinding? = null
    private val binding get() = _binding!!
    private val playlistViewModel: PlaylistViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setSortPlaylistButton()
        setOnAddButtonClickListener()
        setSettingButton()
    }

    private fun setSettingButton() {
        binding.buttonSettings.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, SettingsFragment())
                .commit()
        }
    }

    private fun setOnAddButtonClickListener() {
        binding.buttonAddPlaylist.setOnClickListener {
            launchPlaylistSongsFragment(Playlist(-1, "", ""), true)
        }
    }

    private fun setSortPlaylistButton() {
        binding.buttonSort.setOnClickListener {
            SortDialogFragment.newInstance(playlistViewModel.getCurrentSorting(), false)
                .show(parentFragmentManager, SORT_DIALOG_TAG_PL)
        }
        parentFragmentManager.setFragmentResultListener(
            SORT_DIALOG_RES_PL,
            viewLifecycleOwner
        ) { _, res ->
            playlistViewModel.sortMusicList(
                listOf(res.getInt(NEW_SORT_NAME_PL), res.getInt(NEW_SORT_TYPE_PL))
            )
        }
    }

    private fun setAdapter(): PlaylistRecyclerAdapter {
        val recyclerView = binding.playlistRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = PlaylistRecyclerAdapter(requireContext(), emptyList(), this)
        val adapter = recyclerView.adapter as PlaylistRecyclerAdapter
        val playlistMediatorLiveData = playlistViewModel.getPlaylistMediatorLiveData()
        playlistMediatorLiveData.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }
        return adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        playlistViewModel.saveCurrentSorting()
    }

    private fun launchPlaylistSongsFragment(playlist: Playlist, isAddNewPlaylist: Boolean) {
        val playlistId = if (isAddNewPlaylist) playlistViewModel.getIdForNewPlaylist() else 0
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.fragment_container,
                PlaylistSongsFragment.newInstance(playlist, playlistId)
            )
            .commit()
    }

    override fun onPlaylistClickAction(playlist: Playlist) {
        launchPlaylistSongsFragment(playlist, false)
    }
}