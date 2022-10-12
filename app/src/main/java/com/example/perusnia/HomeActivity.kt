package com.example.perusnia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookList: ArrayList<Book>
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //inialissi recycleview
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL, false)

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







        //---------------navigation-----------------------------------
        bottom_navigation.selectedItemId = R.id.home

        // Perform item selected listener
        bottom_navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> return@OnNavigationItemSelectedListener true
                R.id.favorite -> {
                    startActivity(Intent(applicationContext, FavoriteActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.note -> {
                    startActivity(Intent(applicationContext, NoteActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    startActivity(Intent(applicationContext, ProfileActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
        //--------------------navigation-----------------------------------
        btn_ViewMore.setOnClickListener{
            val intent = Intent(this@HomeActivity, BookActivity::class.java)
            startActivity(intent)
        }
        btn_cart.setOnClickListener{
            val intent = Intent(this@HomeActivity, CartActivity::class.java)
            startActivity(intent)
        }
    }
}