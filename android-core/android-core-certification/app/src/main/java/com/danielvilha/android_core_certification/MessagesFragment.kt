package com.danielvilha.android_core_certification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.danielvilha.android_core_certification.databinding.FragmentMessagesBinding
import com.google.android.material.snackbar.Snackbar


/**
 * A simple [Fragment] subclass.
 * Use the [MessagesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MessagesFragment : Fragment() {
    private var _binding: FragmentMessagesBinding? = null
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        // https://www.baeldung.com/kotlin/concatenate-strings
        binding?.buttonToast?.setOnClickListener {
            Toast.makeText(context, "${getString(R.string.message)} ${getString(R.string.toast)}", Toast.LENGTH_LONG).show()
        }

        binding?.buttonSnackbar?.setOnClickListener {
            Snackbar.make(binding?.constraint!!, "${getString(R.string.message)} ${getString(R.string.snackbar)}", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MessagesFragment.
         */
        @JvmStatic
        fun newInstance() =
            MessagesFragment().apply {}
    }
}