package com.example.music.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.music.MusicViewModel
import com.example.music.MusicViewModel.Companion.searchTextLiveData
import com.example.music.MusicViewModel.Companion.setSearchText
import com.example.music.R
import com.example.music.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), MusicRecyclerAdapter.OnMusicClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val musicViewModel: MusicViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter: MusicRecyclerAdapter? = setAdapter()
        setSearchView(adapter)
    }

    private fun setSearchView(adapter: MusicRecyclerAdapter?) {
        val searchView = activity?.findViewById<SearchView>(R.id.search_view)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                setSearchText(newText.orEmpty())
                return true
            }
        })
        searchTextLiveData.observe(viewLifecycleOwner) {
            adapter?.updateData(musicViewModel.getFilteredListMusic())
        }
    }

    private fun setAdapter(): MusicRecyclerAdapter? {
        val recyclerView = binding.musicRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = MusicRecyclerAdapter(requireContext(), emptyList(),this)
        val adapter = recyclerView.adapter as? MusicRecyclerAdapter
        val musicMediatorLiveData = musicViewModel.getMusicMediatorLiveData()
        musicMediatorLiveData.observe(viewLifecycleOwner) {
            adapter?.updateData(it)
        }
        return adapter
    }
}