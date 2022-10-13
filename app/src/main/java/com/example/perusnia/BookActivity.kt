package com.example.perusnia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_book.*

class BookActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookList: ArrayList<Book>
    private lateinit var bookAdapter: BookAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)


        //inialissi recycleview
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        bookList = ArrayList()

        //isi varible booklist dengn arraylist
        val bookList = bookList

        //isi data varible bookList dengan tipe data array list
        for (i in 1..20) {
            bookList.add(
                Book(
                    R.drawable.book,
                    "Buku " + i,
                    "Salman Alrosyid " +i,
                    "Rp. 45.000 " + i,
                    "09 September 2022 "+ i,
                    "4.5/5 " + i,
                    23,
                    200,
                    "kurangnya bahan literasi bagi masyarakat terutama para kolektor Numismatik " +
                            "di Nusantara. Walau sudah ada beberapa buku literasi Numismatik seperti buku " +
                            "katalog, penulis merasa hal itu masih kurang untuk mendalami sejarah perkembangan " +
                            "uang yang ada di seluruh dunia terutama di Indonesia ini, yang saat itu banyak " +
                            "berdirinya kerajaan dan kesultanan di wilayah Nusantara. Sehingga menyulitkan " +
                            "bagi para Kolektor Numismatik untuk mengetahui dan memahami uang pada masa itu."

                )
            )
        }

        //kirimkan data ke bookAdapter
        bookAdapter = BookAdapter(bookList)
        recyclerView.adapter = bookAdapter


        //beri fungsi onitemclick yang terdapat pada bookAdapter edngan men start activity bookdetail, dengan data/putextra book dengan value list yang di kirim
        bookAdapter.onItemClick = {
            val intent = Intent(this, BookDetileActivity::class.java)
            intent.putExtra("book",it)
            startActivity(intent)
        }

        btn_back.setOnClickListener {
            finish()
        }
    }
}