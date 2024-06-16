package com.myrent.capstoneproject.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myrent.capstoneproject.R

class AdapterHome : RecyclerView.Adapter<AdapterHome.HomeViewHolder>() {
    private val items = listOf("Item 1", "Item 2", "Item 3") // Sample data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        // Bind data to ViewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize itemView components
    }
}