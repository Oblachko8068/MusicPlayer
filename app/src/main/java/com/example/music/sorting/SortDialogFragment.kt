package com.example.music.sorting

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.music.R
import com.example.music.databinding.FragmentSortDialogBinding
import kotlin.math.abs

const val SORT_DIALOG_TAG = "SORT_DIALOG_TAG"
const val SORT_NAME_KEY = "SORT_NAME_KEY"
const val SORT_TYPE_KEY = "SORT_TYPE_KEY"
const val NEW_SORT_NAME = "newSortName"
const val NEW_SORT_TYPE = "newSortType"
const val SORT_DIALOG_RES = "sortDialogResult"

const val SORT_DIALOG_TAG_PL = "SORT_DIALOG_TAG_PL"
const val NEW_SORT_NAME_PL = "newSortNamePl"
const val NEW_SORT_TYPE_PL = "newSortTypePl"
const val SORT_DIALOG_RES_PL = "sortDialogResultPl"

const val SORT_ENTITY_KEY = "SORT_ENTITY_KEY"
const val xLocation = 35
const val yLocation = 128

class SortDialogFragment : DialogFragment() {

    override fun getTheme(): Int = R.style.RoundedCornersDialog
    private var _binding: FragmentSortDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        setDialogLocation()
        _binding = FragmentSortDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setDialogLocation() {
        dialog!!.window?.attributes = WindowManager.LayoutParams().apply {
            gravity = Gravity.TOP or Gravity.END
            x = xLocation
            y = yLocation
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sortName = arguments?.getInt(SORT_NAME_KEY) ?: 0
        val sortType = arguments?.getInt(SORT_TYPE_KEY) ?: 0
        val isMusicSorting = arguments?.getBoolean(SORT_ENTITY_KEY) ?: true
        binding.sortingListView.adapter = SortingViewAdapter(
            context = requireContext(),
            sortNames = resources.getStringArray(
                if (isMusicSorting) R.array.sort_music_titles else R.array.sort_playlist_titles
            ),
            currentSorting = arrayOf(sortName, sortType)
        )

        binding.sortingListView.setOnItemClickListener { _, _, i: Int, _ ->
            val resultData = Bundle()
            resultData.putInt(if (isMusicSorting) NEW_SORT_NAME else NEW_SORT_NAME_PL, i)
            resultData.putInt(
                if (isMusicSorting) NEW_SORT_TYPE else NEW_SORT_TYPE_PL,
                if (i == sortName) abs(sortType - 1) else 1
            )
            setFragmentResult(
                if (isMusicSorting) SORT_DIALOG_RES else SORT_DIALOG_RES_PL,
                resultData
            )
            dismiss()
        }
    }

    companion object {
        fun newInstance(currentSorting: List<Int>, isMusicSorting: Boolean): SortDialogFragment =
            SortDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt(SORT_NAME_KEY, currentSorting[0])
                    putInt(SORT_TYPE_KEY, currentSorting[1])
                    putBoolean(SORT_ENTITY_KEY, isMusicSorting)
                }
            }
    }
}