package com.example.music.sorting

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.example.music.R
import com.example.music.databinding.SortListViewBinding

class SortingViewAdapter(
    private val context: Context,
    private val sortNames: Array<String>,
    private val currentSorting: Array<Int>
) : ArrayAdapter<String>(context, R.layout.sort_list_view, sortNames) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = SortListViewBinding.inflate(LayoutInflater.from(context))
        binding.listItemText.text = sortNames[position]

        if (position == currentSorting[0]) {
            binding.listItem.setBackgroundResource(R.color.played_video)
            binding.listItemText.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.text_of_the_tabs_active_dark
                )
            )
            binding.listItemText.setBackgroundResource(R.color.transparent)
            if (currentSorting[1] == 1) {
                binding.listItemImage.setImageResource(R.drawable.icon_sort_up)
            }
        }

        return binding.root
    }
}