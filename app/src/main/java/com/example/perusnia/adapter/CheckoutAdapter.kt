package com.example.perusnia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perusnia.Model.DataX
import com.example.perusnia.R
import com.example.perusnia.Retrofit.RetrofitClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_item.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CheckoutAdapter(val data:ArrayList<DataX>):RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.checkout_item_detile,parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cart = data[position]
        Picasso.get()
            .load("${RetrofitClient.BASE_URL}api/files.php?api_key=fasih123&file=${cart.cover}")
            .placeholder(R.drawable.default_image)
            .error(R.drawable.default_image)
            .into(holder.view.image)
        holder.view.judul.text = cart.judul
        holder.view.Pengarang.text = cart.author
        holder.view.publisher_name.text = cart.publisherName
        holder.view.price.text = if (cart.harga!!.toInt() != 0) "Rp."+ NumberFormat.getNumberInstance(
            Locale.US).format(cart.harga!!.toInt()) else "Free"

    }

    override fun getItemCount() = data.size

    class ViewHolder(val view: View):RecyclerView.ViewHolder(view)

    fun setData(data2:List<DataX>){
        data.clear()
        data.addAll(data2)
        notifyDataSetChanged()
    }
}