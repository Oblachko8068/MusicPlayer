package com.example.music.optionDialogs

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.music.R
import com.example.music.databinding.FragmentOptionDialogBinding
import com.example.music.sorting.xLocation
import com.example.music.sorting.yLocation

const val OPT_DIALOG_TAG = "OPT_DIALOG_TAG"
const val OPT_DIALOG_RES = "OPT_DIALOG_RES"
const val OPT_ARRAY = "OPT_ARRAY"
const val OPT_RES_POSITION = "OPT_RES_POSITION"
const val OPT_RESULT = "OPT_RESULT"

class OptionDialogFragment : DialogFragment() {

    override fun getTheme(): Int = R.style.RoundedCornersDialog
    private var _binding: FragmentOptionDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setDialogLocation()
        _binding = FragmentOptionDialogBinding.inflate(inflater, container, false)
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
        val optionsArray = arguments?.getStringArray(OPT_ARRAY) as Array<String>
        binding.optionsListView.adapter = OptionsViewAdapter(
            requireContext(),
            optionsArray
        )

        binding.optionsListView.setOnItemClickListener { _, _, position, _ ->
            val resultData = Bundle()
            resultData.putInt(OPT_RES_POSITION, position)
            setFragmentResult(OPT_RESULT, resultData)
            dismiss()
        }
    }

    companion object {
        fun newInstance(optionsList: Array<String>) =
            OptionDialogFragment().apply {
                arguments = Bundle().apply {
                    putStringArray(OPT_ARRAY, optionsList)
                }
            }
    }
}