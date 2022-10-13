package com.example.perusnia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_favorite.bottom_navigation
import kotlinx.android.synthetic.main.activity_home.*


class FavoriteActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookList: ArrayList<Book>
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        recyclerView = findViewById(R.id.recyclerview1)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        bookList = ArrayList()

        val bookList = bookList

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

        bookAdapter = BookAdapter(bookList)
        recyclerView.adapter = bookAdapter

        bookAdapter.onItemClick = {
            val intent = Intent(this, BookDetileActivity::class.java)
            intent.putExtra("book",it)
            startActivity(intent)
        }







        //---------------navigation-----------------------------------
        bottom_navigation.selectedItemId = R.id.favorite

        // Perform item selected listener
        bottom_navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.favorite -> return@OnNavigationItemSelectedListener true
                R.id.home -> {
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
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
    }
}