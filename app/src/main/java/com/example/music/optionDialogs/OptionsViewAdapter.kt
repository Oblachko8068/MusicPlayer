package com.example.music.optionDialogs

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.music.R
import com.example.music.databinding.OptionListViewBinding

class OptionsViewAdapter(
    private val context: Context,
    private val optionsArray: Array<String>
) : ArrayAdapter<String>(context, R.layout.option_list_view) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = OptionListViewBinding.inflate(LayoutInflater.from(context))
        binding.listItemText.text = optionsArray[position]
        return binding.root
    }
}