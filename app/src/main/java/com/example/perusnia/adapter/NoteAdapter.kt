package com.example.perusnia.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perusnia.Model.DataXXX
import com.example.perusnia.Model.DataXXXX
import com.example.perusnia.R
import kotlinx.android.synthetic.main.note_item.view.*
import java.text.SimpleDateFormat

class NoteAdapter(val data: ArrayList<DataXXXX>,val listener: OnAdapterListener):RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = data[position]

        holder.view.judul.text = note.judul
        holder.view.isi.text = Html.fromHtml(Html.fromHtml(note.isi ).toString())
        holder.view.tgl_note.text = SimpleDateFormat("dd MMMM yyyy").format(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(note.createdAt))

        holder.view.setOnClickListener{
            listener.onClick(note)
        }

        holder.view.setOnLongClickListener{
             listener.onLongClick(note)
            false
        }
    }

    override fun getItemCount() = data.size

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

    fun setData(data2:List<DataXXXX>){
        data.clear()
        data.addAll(data2)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick( data: DataXXXX)
        fun onLongClick( data: DataXXXX)
    }
}