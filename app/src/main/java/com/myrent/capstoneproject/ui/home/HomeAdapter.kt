package com.myrent.capstoneproject.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myrent.capstoneproject.R
import com.myrent.capstoneproject.databinding.ItemCardBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var items = listOf(
        CarItem("Honda Brio", "Manual\n6 Orang", "Alvian Trans", "Rp. 350.000/Hari", R.drawable.hondabrio),
        CarItem("Toyota Avanza", "Manual\n6 Orang", "Almoreno", "Rp. 500.000/Hari", R.drawable.toyotaavanza),
        CarItem("Daihatsu Luxio", "Manual\n6 Orang", "Alvian Trans", "Rp. 500.000/Hari", R.drawable.luxio),
        CarItem("Isuzu Elf", "Manual\n20 Orang", "Anugrah Transport", "Rp. 800.000/Hari", R.drawable.elf),
        CarItem("Mitsubishi Xpander", "Manual\n7 Orang", "Alvian Trans", "Rp. 400.000/Hari", R.drawable.mitsubishixpander),
        CarItem("Honda Beat Street", "Automatic\n2 Orang", "Arif Hani", "Rp. 75.000/Hari", R.drawable.honda_beat_street),
        CarItem("Honda Scoopy", "Automatic\n2 Orang", "Arif Hani", "Rp. 85.000/Hari", R.drawable.honda_scoopy),
        CarItem("Honda Vario 125", "Automatic\n2 Orang", "Arif Hani", "Rp. 85.000/Hari", R.drawable.honda_vario_125),
        CarItem("Yamaha NMAX 155", "Automatic\n2 Orang", "Arif Hani", "Rp. 130.000/Hari", R.drawable.yamaha_nmax_155),
        CarItem("Honda PCX", "Automatic\n2 Orang", "Arif Hani", "Rp. 130.000/Hari", R.drawable.honda_pcx)
    )
    private var filteredItems = items.toList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class HomeViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CarItem) {
            binding.carName.text = item.name
            binding.spesifikasi.text = item.spesifikasi
            binding.namaPemilik.text = item.namapemilik
            binding.harga.text = item.harga
            binding.imageCar.setImageResource(item.imagecar)
//            Glide.with(binding.root.context).load(item.imagecar).into(binding.imageCar)
        }
    }
    fun setItems(newItems: List<CarItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    data class CarItem(
        val name: String,
        val spesifikasi: String,
        val namapemilik: String,
        val harga: String,
        val imagecar: Int)

    fun filterItems(filter: String) {
        filteredItems = if (filter.isEmpty()) {
            items
        } else {
            items.filter { it.name.contains(filter, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }
}