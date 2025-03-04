package com.example.music.confirmDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.music.R
import com.example.music.databinding.FragmentConfirmDialogBinding

const val CONFIRM_TITLE = "CONFIRM_TITLE"
const val CONFIRM_RES = "CONFIRM_RES"
const val IS_RES_YES = "IS_RES_YES"

class ConfirmDialogFragment : DialogFragment() {

    override fun getTheme(): Int = R.style.RoundedCornersDialog
    private var _binding: FragmentConfirmDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = arguments?.getString(CONFIRM_TITLE).toString()
        binding.buttonNo.setOnClickListener {
            setDialogResult(isYes = false)
        }
        binding.buttonYes.setOnClickListener {
            setDialogResult(isYes = true)
        }
    }

    private fun setDialogResult(isYes: Boolean) {
        setFragmentResult(
            CONFIRM_RES,
            Bundle().apply { putBoolean(IS_RES_YES, isYes) }
        )
        dismiss()
    }

    companion object {
        fun newInstance(title: String): ConfirmDialogFragment = ConfirmDialogFragment().apply {
            arguments = Bundle().apply {
                putString(CONFIRM_TITLE, title)
            }
        }
    }
}