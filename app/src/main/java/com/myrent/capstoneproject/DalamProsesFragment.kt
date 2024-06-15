//package com.myrent.capstoneproject
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.myrent.capstoneproject.ui.order.OrderAdapter
//import com.myrent.capstoneproject.ui.order.OrderItem
//
//class DalamProsesFragment : Fragment() {
//
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: OrderAdapter
//    private lateinit var orderList: List<OrderItem>
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_dalam_proses, container, false)
//
//        recyclerView = view.findViewById(R.id.rv_proses)
//        recyclerView.layoutManager = LinearLayoutManager(context)
//
//        orderList = listOf(
//            OrderItem("Order 1", "Description 1"),
//            OrderItem("Order 2", "Description 2")
//        )
//
//        adapter = OrderAdapter(orderList)
//        recyclerView.adapter = adapter
//
//        return view
//    }
//}