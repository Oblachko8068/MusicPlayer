package com.example.music

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.music.databinding.ItemPlaylistLayoutBinding

class PlaylistRecyclerAdapter(
    private val context: Context,
    private val itemCLickListener: onPlaylistClickListener,
) : RecyclerView.Adapter<PlaylistRecyclerAdapter.ViewHolder>() {

    interface onPlaylistClickListener {

    }

    class ViewHolder(
        binding: ItemPlaylistLayoutBinding,
        itemClickListener: onPlaylistClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPlaylistLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemCLickListener)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}