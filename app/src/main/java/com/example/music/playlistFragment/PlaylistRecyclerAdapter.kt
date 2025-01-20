package com.example.music.playlistFragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Playlist
import com.example.music.R
import com.example.music.databinding.ItemPlaylistLayoutBinding

class PlaylistRecyclerAdapter(
    private val context: Context,
    private var playlistList: List<Playlist>,
    private val itemCLickListener: OnPlaylistClickListener,
) : RecyclerView.Adapter<PlaylistRecyclerAdapter.ViewHolder>() {

    interface OnPlaylistClickListener {

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newPlaylistList: List<Playlist>) {
        playlistList = newPlaylistList
        notifyDataSetChanged()
    }
    class ViewHolder(
        private val binding: ItemPlaylistLayoutBinding,
        itemClickListener: OnPlaylistClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentPlaylist: Playlist, context: Context) {
            binding.playlistName.text = currentPlaylist.name
            binding.playlistImage.setBackgroundResource(R.drawable.icon_music_pause)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPlaylistLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemCLickListener)
    }

    override fun getItemCount(): Int = playlistList.size + 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == playlistList.size + 1){
            holder.bind(Playlist(-1, "Новый"), context)
            holder.itemView.setOnClickListener {

            }
        }
        val currentPlaylist = playlistList[position]
        holder.bind(currentPlaylist, context)
        holder.itemView.setOnClickListener {

        }
    }
}