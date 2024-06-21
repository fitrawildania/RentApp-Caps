package com.myrent.capstoneproject.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.myrent.capstoneproject.ui.detail.DetailActivity
import com.myrent.capstoneproject.databinding.FragmentHomeBinding
import com.myrent.capstoneproject.model.CarItem
import com.myrent.capstoneproject.ml.MatrixFactorizationModel
import com.myrent.capstoneproject.ui.modeling.ModelingActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

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

        // Button to start ModelingActivity
        binding.startModelingActivityButton.setOnClickListener {
            val intent = Intent(requireContext(), ModelingActivity::class.java)
            startActivityForResult(intent, MODELING_ACTIVITY_REQUEST_CODE)
        }
    }

    private fun viewRecommendation() {
        try {
            // Create an instance of the model
            val model = MatrixFactorizationModel.newInstance(requireContext())

            // Prepare input data (example data, adjust as per your model)
            val userByteBuffer = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).putFloat(1f) // Example user ID
            userByteBuffer.rewind()
            val itemByteBuffer = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).putFloat(1f) // Example item ID
            itemByteBuffer.rewind()

            // Create TensorBuffer for input
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1), DataType.FLOAT32)
            inputFeature0.loadBuffer(userByteBuffer)
            val inputFeature1 = TensorBuffer.createFixedSize(intArrayOf(1), DataType.FLOAT32)
            inputFeature1.loadBuffer(itemByteBuffer)

            // Run model inference and get result
            val outputs = model.process(inputFeature0, inputFeature1)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            // Display the recommendation result
            displayRecommendations(outputFeature0.floatArray)

            // Release model resources if no longer used
            model.close()
        } catch (e: Exception) {
            Log.e("HomeFragment", "Error in viewRecommendation: ${e.message}", e)
        }
    }

    private fun displayRecommendations(recommendations: FloatArray) {
        recommendations.forEach { recommendation ->
            Log.d("Recommendation", recommendation.toString())
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MODELING_ACTIVITY_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            data?.let {
                val rating = it.getIntExtra("rating", 0)
                val harga = it.getIntExtra("harga", 0)
                val kapasitas = it.getIntExtra("kapasitas", 0)
                val recommendationScore = it.getFloatExtra("recommendationScore", 0f)

                // Use the returned data as needed
                Log.d("HomeFragment", "Rating: $rating, Harga: $harga, Kapasitas: $kapasitas, Recommendation: $recommendationScore")
            }
        }
    }

    companion object {
        const val MODELING_ACTIVITY_REQUEST_CODE = 1
    }
}
