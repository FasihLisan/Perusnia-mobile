package com.example.perusnia

import android.content.Intent
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout.Mode
import kotlinx.android.synthetic.main.activity_book_detile.*


class BookDetileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detile)

        val book = intent.getParcelableExtra<Book>("book")
        if (book != null){
            GambarBuku.setImageResource(book.GambarBuku)
            GambarBukuGradient.setImageResource(book.GambarBuku)
            judulBuku.text = book.JudulBuku
            Pengarang.text = book.Pengarang
            TglUpload.text = book.TglUpload
            RateBukuValue.text = book.Rate
            ReadBookValue.text = book.Read.toString()
            pageBookValue.text = book.Page.toString()
            sinopsisBuku.text = book.Sinopsis
        }


        btn_back.setOnClickListener{
           finish()
        }
        btn_addFavorite.setOnClickListener{
            btn_addFavorite.setColorFilter(ContextCompat.getColor(this,R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
        }

    }
}