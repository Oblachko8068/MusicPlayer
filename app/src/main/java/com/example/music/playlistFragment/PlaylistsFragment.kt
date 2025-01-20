package com.example.music.playlistFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.music.PlaylistViewModel
import com.example.music.databinding.FragmentPlaylistsBinding

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
        val adapter: PlaylistRecyclerAdapter = setAdapter()

    }

    private fun setAdapter(): PlaylistRecyclerAdapter {
        val recyclerView = binding.playlistRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = PlaylistRecyclerAdapter(requireContext(), emptyList(),this)
        val adapter = recyclerView.adapter as PlaylistRecyclerAdapter
        val playlistMediatorLiveData = playlistViewModel.getPlaylistMediatorLiveData()
        playlistMediatorLiveData.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }
        return adapter
    }
}