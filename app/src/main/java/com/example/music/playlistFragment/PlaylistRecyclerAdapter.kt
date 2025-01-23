package com.example.music.playlistFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
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

    private class DiffUtilCallback(
        private val oldPlaylistList: List<Playlist>,
        private val newPlaylistList: List<Playlist>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldPlaylistList.size

        override fun getNewListSize(): Int = newPlaylistList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldPlaylistList[oldItemPosition].javaClass == newPlaylistList[newItemPosition].javaClass

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldPlaylistList[oldItemPosition].hashCode() == newPlaylistList[newItemPosition].hashCode()

    }

    fun updateData(newPlaylistList: List<Playlist>) {
        val diffCallback = DiffUtilCallback(playlistList, newPlaylistList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        playlistList = newPlaylistList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(
        private val binding: ItemPlaylistLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentPlaylist: Playlist, context: Context) {
            binding.playlistName.text = currentPlaylist.name
            binding.playlistImage.setBackgroundResource(R.drawable.icon_music_pause)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPlaylistLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = playlistList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPlaylist = playlistList[position]
        holder.bind(currentPlaylist, context)
        holder.itemView.setOnClickListener {

        }
    }
}