package com.rundgrun.ui.primorskiy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rundgrun.databinding.FragmentHomeBinding
import com.rundgrun.ui.models.Item

class PrimorskiyFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var primorskiyViewModel: PrimorskiyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        primorskiyViewModel =
            ViewModelProvider(this).get(PrimorskiyViewModel::class.java)


        val root: View = binding.root

        val adapter = InstrumentRecyclerAdapter(object : ItemActionListeners {
            override fun onItemDetails(item: Item) {

            }
        })
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        primorskiyViewModel.items.observe(viewLifecycleOwner) {
            adapter.items = it
            println(it.size)
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
        ContextCompat.startActivity(requireContext(), urlIntent, null)
    }
}