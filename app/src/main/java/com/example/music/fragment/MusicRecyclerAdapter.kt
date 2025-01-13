package com.example.music.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.music.databinding.ItemMusicLayoutBinding

class MusicRecyclerAdapter(
    private val context: Context,
    private val itemClickListener: OnMusicClickListener,
) : RecyclerView.Adapter<MusicRecyclerAdapter.ViewHolder>() {

    interface OnMusicClickListener {

    }

    class ViewHolder(
        binding: ItemMusicLayoutBinding,
        itemClickListener: OnMusicClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

       fun bind() {

       }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemMusicLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: MusicRecyclerAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}