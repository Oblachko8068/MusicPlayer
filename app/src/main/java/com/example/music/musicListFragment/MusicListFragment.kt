package com.example.music.musicListFragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.Music
import com.example.music.R
import com.example.music.databinding.FragmentHomeBinding
import com.example.music.fragment.MusicPlayerFragment
import com.example.music.fragment.SettingsFragment
import com.example.music.service.MusicService
import com.example.music.sorting.*

class MusicListFragment : Fragment(), MusicRecyclerAdapter.OnMusicClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val musicViewModel: MusicViewModel by activityViewModels()
    private var musicService: MusicService? = null
    private var isServiceBound = false
    private lateinit var musicAdapter: MusicRecyclerAdapter

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicService.MusicBinder
            musicService = binder.service
            musicService?.musicList = musicViewModel.getMusicList()
            isServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
            isServiceBound = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupServiceConnection()
        setupButtons()
    }

    private fun setupAdapter() {
        musicAdapter = MusicRecyclerAdapter(requireContext(), emptyList(), this)
        binding.musicRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = musicAdapter
        }

        musicViewModel.getMusicMediatorLiveData().observe(viewLifecycleOwner) { list ->
            musicAdapter.updateData(list)
            musicService?.musicList = list
        }
    }

    private fun setupServiceConnection() {
        Intent(requireContext(), MusicService::class.java).also { intent ->
            requireActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun setupButtons() {
        binding.buttonSettings.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SettingsFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.buttonSort.setOnClickListener {
            SortDialogFragment.newInstance(musicViewModel.getCurrentSorting(), true)
                .show(parentFragmentManager, SORT_DIALOG_TAG)
        }
    }

    override fun onMusicClickAction(music: Music) {
        val position = musicViewModel.getMusicList().indexOf(music)
        if (position != -1) {
            musicService?.playMusicAtPosition(position)
            parentFragmentManager.beginTransaction()
                .replace(R.id.music_player_container, MusicPlayerFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isServiceBound) {
            requireActivity().unbindService(serviceConnection)
        }
        _binding = null
    }
}