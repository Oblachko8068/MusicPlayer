package com.example.music.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.music.R
import com.example.music.databinding.FragmentMusicPlayerBinding
import com.example.music.service.MusicService

class MusicPlayerFragment : Fragment() {

    private var _binding: FragmentMusicPlayerBinding? = null
    private val binding get() = _binding!!
    private var musicService: MusicService? = null
    private var isServiceBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicService.MusicBinder
            musicService = binder.service
            isServiceBound = true
            setupPlayerControls()
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
        _binding = FragmentMusicPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindToService()
    }

    private fun bindToService() {
        Intent(requireContext(), MusicService::class.java).also { intent ->
            requireActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun setupPlayerControls() {
        binding.startStopMusic.setOnClickListener {
            musicService?.let { service ->
                if (service.isPlaying) service.pausePlayer() else service.startPlayer()
                updatePlayButton(service.isPlaying)
            } ?: run {
                Toast.makeText(requireContext(), "Service not available", Toast.LENGTH_SHORT).show()
            }
        }

        binding.nextMusic.setOnClickListener {
            musicService?.playNext() ?: run {
                Toast.makeText(requireContext(), "Service not available", Toast.LENGTH_SHORT).show()
            }
        }

        binding.prevMusic.setOnClickListener {
            musicService?.playPrevious() ?: run {
                Toast.makeText(requireContext(), "Service not available", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updatePlayButton(isPlaying: Boolean) {
        val icon = if (isPlaying) R.drawable.icon_music_pause else R.drawable.icon_music_play
        binding.startStopMusic.setImageResource(icon)
    }

    override fun onDestroyView() {
        if (isServiceBound) {
            requireActivity().unbindService(serviceConnection)
        }
        super.onDestroyView()
        _binding = null
    }
}
