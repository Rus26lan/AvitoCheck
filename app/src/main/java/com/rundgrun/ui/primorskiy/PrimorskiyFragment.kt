package com.rundgrun.ui.primorskiy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rundgrun.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class PrimorskiyFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var primorskiyViewModel: PrimorskiyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        primorskiyViewModel =
            ViewModelProvider(this).get(PrimorskiyViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        primorskiyViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
            runLink(it)
        }
        return root
    }

    override fun onStart() {
        super.onStart()
        primorskiyViewModel.readData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun runLink(url: String) {
        val urlIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
        ContextCompat.startActivity(requireContext(),urlIntent, null)
    }
}