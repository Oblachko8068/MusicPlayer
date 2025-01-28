package com.example.music.homeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.Music
import com.example.music.homeFragment.MusicViewModel.Companion.searchTextLiveData
import com.example.music.homeFragment.MusicViewModel.Companion.setSearchText
import com.example.music.R
import com.example.music.databinding.FragmentHomeBinding
import com.example.music.sorting.NEW_SORT_NAME
import com.example.music.sorting.NEW_SORT_TYPE
import com.example.music.sorting.SORT_DIALOG_RES
import com.example.music.sorting.SORT_DIALOG_TAG
import com.example.music.sorting.SortDialogFragment

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
        val adapter: MusicRecyclerAdapter = setAdapter()
        setSearchView(adapter)
        setSortMusicButton()
        //setSettingButton()
    }

    private fun setSettingButton() {
        TODO("Запуск  настроек")
    }

    private fun setSortMusicButton() {
        binding.buttonSort.setOnClickListener {
            val sortDialogFragment =
                SortDialogFragment.newInstance(musicViewModel.getCurrentSorting(), true)
            sortDialogFragment.show(parentFragmentManager, SORT_DIALOG_TAG)
        }
        parentFragmentManager.setFragmentResultListener(
            SORT_DIALOG_RES,
            viewLifecycleOwner
        ) { _, res ->
            musicViewModel.sortMusicList(
                listOf(res.getInt(NEW_SORT_NAME), res.getInt(NEW_SORT_TYPE))
            )
        }
    }

    private fun setSearchView(adapter: MusicRecyclerAdapter?) {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

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

    private fun setAdapter(): MusicRecyclerAdapter {
        val recyclerView = binding.musicRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = MusicRecyclerAdapter(requireContext(), emptyList(), this)
        val adapter = recyclerView.adapter as MusicRecyclerAdapter
        val musicMediatorLiveData = musicViewModel.getMusicMediatorLiveData()
        musicMediatorLiveData.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }
        return adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        musicViewModel.saveCurrentSorting()
    }

    override fun omMusicClickAction(music: Music) {

    }
}