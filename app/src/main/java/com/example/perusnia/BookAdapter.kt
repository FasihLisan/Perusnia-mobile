package com.example.perusnia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//dengan paramere booklist tipe data array list
class BookAdapter(private val bookList:ArrayList<Book>)
    : RecyclerView.Adapter<BookAdapter.BookViewHolder>(){

    var onItemClick : ((Book) -> Unit)? = null

    //menandai setiap id yang ingin di set data
    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val gambarBuku: ImageView = itemView.findViewById(R.id.GambarBuku)
        val judulBuku: TextView = itemView.findViewById(R.id.judulBuku)
        val Pengarang: TextView = itemView.findViewById(R.id.Pengarang)
        val Harga: TextView = itemView.findViewById(R.id.Harga)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        //inialisasi layout yang ingin di set data
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_card_design,parent,false)
        return  BookViewHolder(view)
    }

    //melakukan set data
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.gambarBuku.setImageResource(book.GambarBuku)
        holder.judulBuku.text = book.JudulBuku
        holder.Pengarang.text = book.Pengarang
        holder.Harga.text = book.Harga

        //beri action clik pada itemview
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(book)
        }
    }

    override fun getItemCount(): Int {
        return  bookList.size
    }
}