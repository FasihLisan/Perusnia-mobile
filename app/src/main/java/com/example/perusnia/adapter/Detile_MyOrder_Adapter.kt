package com.example.perusnia.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perusnia.Model.DataXXXXXXX
import com.example.perusnia.R
import kotlinx.android.synthetic.main.detile_item_order.view.*
import kotlinx.android.synthetic.main.myorder_item.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class Detile_MyOrder_Adapter(val data: ArrayList<DataXXXXXXX>): RecyclerView.Adapter<Detile_MyOrder_Adapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.detile_item_order,parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val DetileOrder = data[position]

        holder.view.Judul.text = DetileOrder.judul
        holder.view.Harga.text = if (DetileOrder.harga?.toInt() != 0) "Rp."+ NumberFormat.getNumberInstance(
            Locale.US).format(DetileOrder.harga?.toInt()) else "Free"

    }

    override fun getItemCount() = data.size

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

    fun setData(data2:List<DataXXXXXXX>){
        data.clear()
        data.addAll(data2)
        notifyDataSetChanged()
    }

}