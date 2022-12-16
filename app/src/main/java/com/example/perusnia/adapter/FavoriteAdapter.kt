package com.example.perusnia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perusnia.Model.DataX
import com.example.perusnia.R
import com.example.perusnia.Retrofit.RetrofitClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.favorite_book_item.view.*
import kotlinx.android.synthetic.main.favorite_book_item.view.GambarBuku
import kotlinx.android.synthetic.main.favorite_book_item.view.Harga
import kotlinx.android.synthetic.main.favorite_book_item.view.Pengarang
import kotlinx.android.synthetic.main.favorite_book_item.view.judulBuku
import kotlinx.android.synthetic.main.top_rated_book_item.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class FavoriteAdapter(val data: ArrayList<DataX>,val listener: OnAdapterListener): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.favorite_book_item,parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val book = data[position]
        Picasso.get()
            .load("${RetrofitClient.BASE_URL}api/files.php?api_key=fasih123&file=${book.cover}")
            .placeholder(R.drawable.default_image)
            .error(R.drawable.default_image)
            .into(holder.view.GambarBuku)
        holder.view.judulBuku.text = book.judul
        holder.view.Pengarang.text = book.author
        holder.view.Harga.text = if (book.harga!!.toInt() != 0) "Rp."+ NumberFormat.getNumberInstance(
            Locale.US).format(book.harga!!.toInt()) else "Free"
        holder.view.setOnClickListener{
            listener.onClick(book)
        }
    }

    override fun getItemCount() = data.size

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

    fun setData(data2:List<DataX>){
        data.clear()
        data.addAll(data2)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick( data:DataX )
    }


}