package com.myrent.capstoneproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.myrent.capstoneproject.R
import com.myrent.capstoneproject.data.RetrofitClient
import com.myrent.capstoneproject.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeAdapter: HomeAdapter
    private val apiService = RetrofitClient.apiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeAdapter = HomeAdapter()
        binding.rvHome.layoutManager = LinearLayoutManager(context)
        binding.rvHome.adapter = homeAdapter

        setupFilterButtons()
    }

    private fun setupFilterButtons() {
        binding.btnHomeAll.setOnClickListener { fetchFilteredItems("") }
        binding.btnHomeMobil.setOnClickListener { fetchFilteredItems("mobil") }
        binding.btnHomeMotor.setOnClickListener { fetchFilteredItems("motor") }
        binding.btnHomeBus.setOnClickListener { fetchFilteredItems("bus") }
        binding.btnHomeStar.setOnClickListener { fetchFilteredItems("star") }
        binding.btnHomeLowprices.setOnClickListener { fetchFilteredItems("lowprices") }
    }

    private fun fetchFilteredItems(filter: String) {
        lifecycleScope.launch {
            try {
                val items = apiService.getCars(filter)
                homeAdapter.setItems(items)
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}