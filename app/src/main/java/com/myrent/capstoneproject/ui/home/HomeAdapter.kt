package com.myrent.capstoneproject.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myrent.capstoneproject.databinding.ItemCardBinding
import com.myrent.capstoneproject.model.CarItem

class HomeAdapter(private val onItemClicked: (CarItem) -> Unit) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private val items = mutableListOf<CarItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<CarItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CarItem) {
            binding.carName.text = item.merk_kendaraan
            binding.spesifikasi.text = item.Transmisi
            binding.namaPemilik.text = item.id_pemilik
            binding.harga.text = item.harga_per_hari
            binding.imageCar.setImageResource(item.imagecar)
            itemView.setOnClickListener { onItemClicked(item) }
        }
    }
}
