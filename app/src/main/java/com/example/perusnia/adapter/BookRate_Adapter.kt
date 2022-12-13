package com.example.perusnia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perusnia.Model.DataX
import com.example.perusnia.Model.DataXX
import com.example.perusnia.R
import com.example.perusnia.Retrofit.RetrofitClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.favorite_book_item.view.*
import kotlinx.android.synthetic.main.feedback_book_item.view.*
import java.text.SimpleDateFormat

class BookRate_Adapter(val data: ArrayList<DataXX>):RecyclerView.Adapter<BookRate_Adapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.feedback_book_item,parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rate = data[position]
        Picasso.get()
            .load("${RetrofitClient.BASE_URL}api/files.php?api_key=fasih123&file=${rate.foto}")
            .placeholder(R.drawable.default_image)
            .error(R.drawable.default_image)
            .into(holder.view.foto_feedback)
        holder.view.name_feedback.text = rate.namaLengkap
        holder.view.rating_feedback.rating = rate!!.rateScore!!.toFloat()
        holder.view.tgl_feedback.text = SimpleDateFormat("dd/MM/yyyy").format(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rate.createdAt))
        holder.view.feedback.text = rate.comment
    }

    override fun getItemCount() = data.size

    class ViewHolder(val view:View):RecyclerView.ViewHolder(view)

    fun setData(data2:List<DataXX>){
        data.clear()
        data.addAll(data2)
        notifyDataSetChanged()
    }

}