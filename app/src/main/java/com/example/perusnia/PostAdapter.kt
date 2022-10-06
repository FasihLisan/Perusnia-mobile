package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.perusnia.R

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
//    private val image = arrayOf("@drawable/book",
//        "@drawable/book", "@drawable/book", "@drawable/book",
//        "@drawable/book", "@drawable/book", "@drawable/book",
//        "@drawable/book")

    private val judul = arrayOf("Perkembangan uang seluruh dunia", "Perkembangan uang seluruh dunia",
        "Perkembangan uang seluruh dunia", "Perkembangan uang seluruh dunia",
        "Perkembangan uang seluruh dunia", "Perkembangan uang seluruh dunia",
        "Perkembangan uang seluruh dunia", "Perkembangan uang seluruh dunia")

    private val pengarang = arrayOf("Salman Alrosyid",
        "Salman Alrosyid", "Salman Alrosyid", "Salman Alrosyid",
        "Salman Alrosyid", "Salman Alrosyid", "Salman Alrosyid",
        "Salman Alrosyid")

    private val harga = arrayOf("Rp.45.000",
        "Rp.45.000", "Rp.45.000", "Rp.45.000",
        "Rp.45.000", "Rp.45.000", "Rp.45.000",
        "Rp.45.000")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

//        var itemImage: TextView
        var itemJudul: TextView
        var itemPengarang: TextView
        var itemHarga: TextView


        init {
//            itemImage = itemView.findViewById(R.id.imageBook)
            itemJudul = itemView.findViewById(R.id.judulBuku)
            itemPengarang = itemView.findViewById(R.id.Pengarang)
            itemHarga = itemView.findViewById(R.id.Harga)


            itemView.setOnClickListener {
                var position: Int = getAdapterPosition()
                val context = itemView.context
                val intent = Intent(context, itemView::class.java).apply {
                    putExtra("NUMBER", position)
//                    putExtra("IMAGE", itemImage.text)
                    putExtra("JUDUL", itemJudul.text)
                    putExtra("PENGARANG", itemPengarang.text)
                    putExtra("HARGA", itemHarga.text)

                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_post, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
//        viewHolder.itemImage.text = image[i]
        viewHolder.itemJudul.text = judul[i]
        viewHolder.itemPengarang.text = pengarang[i]
        viewHolder.itemHarga.text = harga[i]


    }

    override fun getItemCount(): Int {
        return judul.size
    }
}