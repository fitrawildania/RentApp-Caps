package com.myrent.capstoneproject.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.myrent.capstoneproject.ui.detail.DetailActivity
import com.myrent.capstoneproject.databinding.FragmentHomeBinding
import com.myrent.capstoneproject.model.CarItem

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        homeAdapter = HomeAdapter { carItem ->
            openDetailActivity(carItem)
        }
        binding.rvHome.layoutManager = LinearLayoutManager(context)
        binding.rvHome.adapter = homeAdapter

        homeViewModel.carItems.observe(viewLifecycleOwner, { items ->
            homeAdapter.updateItems(items)
        })

        homeViewModel.ownersItems.observe(viewLifecycleOwner, { owners ->
            homeAdapter.updateOwners(owners)
        })

        setupFilterButtons()
        fetchFilteredItems("")
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
        homeViewModel.fetchCarsItems(filter)
    }

    private fun openDetailActivity(carItem: CarItem) {
        val intent = Intent(context, DetailActivity::class.java).apply {
            putExtra("carName", carItem.merk_kendaraan)
            putExtra("ownerName", carItem.id_pemilik)
            putExtra("about", carItem.Transmisi)
            putExtra("spesifikasi", carItem.Transmisi)
            putExtra("seatCount", carItem.kapasitas)
            putExtra("carImageResId", carItem.imagecar)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
