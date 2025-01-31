package com.example.music.musicListFragment

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Music
import com.example.music.R
import com.example.music.databinding.ItemMusicLayoutBinding

class MusicRecyclerAdapter(
    private val context: Context,
    private var musicList: List<Music>,
    private val itemClickListener: OnMusicClickListener,
) : RecyclerView.Adapter<MusicRecyclerAdapter.ViewHolder>() {

    interface OnMusicClickListener {

        fun omMusicClickAction(music: Music)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newMusicList: List<Music>) {
        musicList = newMusicList
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: ItemMusicLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentMusic: Music, context: Context) {
            if (currentMusic.artUri.isNotEmpty()) {
                Glide.with(context)
                    .load(Uri.parse(currentMusic.artUri))
                    .placeholder(R.drawable.icon_music_note)
                    .into(binding.musicImage)
            }
            binding.musicName.text = currentMusic.title
            binding.musicText.text = currentMusic.artist
            binding.prolongation.text = formatDuration(currentMusic.duration)
        }
    }

    @SuppressLint("DefaultLocale")
    fun formatDuration(duration: Long): String {
        val minutes = (duration / 1000) / 60
        val seconds = (duration / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemMusicLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMusic = musicList[position]
        holder.bind(currentMusic, context)
        holder.itemView.setOnClickListener {
            itemClickListener.omMusicClickAction(currentMusic)
        }
    }

    override fun getItemCount(): Int = musicList.size
}

/*

val diffCallback = DiffUtilCallBack(musicList, newMusicList)
val diffResult = DiffUtil.calculateDiff(diffCallback)
diffResult.dispatchUpdatesTo(this)

private class DiffUtilCallBack(
        private val oldMusicList: List<Music>,
        private val newMusicList: List<Music>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldMusicList.size

        override fun getNewListSize(): Int = newMusicList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldMusicList[oldItemPosition].javaClass == newMusicList[newItemPosition].javaClass

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldMusicList[oldItemPosition].hashCode() == newMusicList[newItemPosition].hashCode()

    }
*/