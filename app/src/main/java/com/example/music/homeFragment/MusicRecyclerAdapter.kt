package com.example.music.homeFragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.domain.model.Music
import com.example.music.databinding.ItemMusicLayoutBinding

class MusicRecyclerAdapter(
    private val context: Context,
    private var musicList: List<Music>,
    private val itemClickListener: OnMusicClickListener,
) : RecyclerView.Adapter<MusicRecyclerAdapter.ViewHolder>() {

    interface OnMusicClickListener {

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
            if (currentMusic.artUri.isNotEmpty()){
                Glide.with(context)
                    .load(currentMusic.artUri)
                    .transform(RoundedCorners(18))
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

        }
    }

    override fun getItemCount(): Int = musicList.size
}