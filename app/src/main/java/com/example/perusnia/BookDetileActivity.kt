package com.example.perusnia

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.perusnia.Model.DataX
import com.example.perusnia.Model.DefaultResponse
import com.example.perusnia.Model.RateBook_Response
import com.example.perusnia.Model.bookResponse
import com.example.perusnia.Retrofit.RetrofitClient
import com.example.perusnia.storage.SharedPrefManager
import com.like.LikeButton
import com.like.OnLikeListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_book_detile.*
import kotlinx.android.synthetic.main.top_rated_book_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class BookDetileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detile)

        val sharedPrefManager = SharedPrefManager.getInstance(this).user
        val id_users = sharedPrefManager.id_users.toInt()

        val book = intent.getParcelableExtra<DataX>("book")
        if (book != null){
            Picasso.get()
                .load("${RetrofitClient.BASE_URL}api/files.php?api_key=fasih123&file=${book.cover}")
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(GambarBuku)
            Picasso.get()
                .load("${RetrofitClient.BASE_URL}api/files.php?api_key=fasih123&file=${book.cover}")
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(GambarBukuGradient)
            judulBuku.text = book.judul
            Pengarang.text = book.author
            TglUpload.text = book.publicationDate
            RateBukuValue.text = "${book.rateBook}/5"
            ReadBookValue.text = "null"
            pageBookValue.text = "null"
            sinopsisBuku.text = Html.fromHtml(Html.fromHtml(book.description).toString())

            RetrofitClient.instance.getSpesificRateBook("fasih123",id_users.toInt(),book.idBook!!.toInt())
                .enqueue(object : Callback<RateBook_Response?> {
                    override fun onResponse(
                        call: Call<RateBook_Response?>,
                        response: Response<RateBook_Response?>
                    ) {
                        if (response.body()?.status == 200){
                            val data = response.body()?.data
                            btn_ratingBar.rating = data!!.rateScore!!.toFloat()
                            txtFedback.setText(data.comment.toString())
                        }else{
                            btn_ratingBar.rating = 0.0f
                            txtFedback.setText("")
                        }
                    }

                    override fun onFailure(call: Call<RateBook_Response?>, t: Throwable) {
                        Log.d("API response",t.message.toString())
                    }
                })

            RetrofitClient.instance.getSpesificFavorite(id_users,book.idBook!!.toInt())
                .enqueue(object : Callback<DefaultResponse?> {
                    override fun onResponse(
                        call: Call<DefaultResponse?>,
                        response: Response<DefaultResponse?>
                    ) {
                        if (response.body()?.status!!.equals(200)){
                            btn_favorite.setLiked(true)
                        }
                    }

                    override fun onFailure(call: Call<DefaultResponse?>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })



        }




        btn_back.setOnClickListener{
           finish()
        }




        btn_feedback.setOnClickListener {
            val rating = btn_ratingBar.rating
            val comment = txtFedback.text

            if (rating != null){
                TODO("insert to rate_book table")
            }
        }



        btn_favorite.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                RetrofitClient.instance.insertFavorite(id_users,book?.idBook!!.toInt())
                    .enqueue(object : Callback<DefaultResponse?> {
                        override fun onResponse(
                            call: Call<DefaultResponse?>,
                            response: Response<DefaultResponse?>
                        ) {
                            if (response.body()?.status!!.equals(200)){
                                btn_favorite.setLiked(true)
                                Toast.makeText(applicationContext,response.body()?.message,Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(applicationContext,response.body()?.message,Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<DefaultResponse?>, t: Throwable) {
                            Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
                        }
                    })
            }

            override fun unLiked(likeButton: LikeButton?) {
                RetrofitClient.instance.deleteFavorite(id_users,book?.idBook!!.toInt())
                    .enqueue(object : Callback<DefaultResponse?> {
                        override fun onResponse(
                            call: Call<DefaultResponse?>,
                            response: Response<DefaultResponse?>
                        ) {
                            if (response.body()?.status!!.equals(200)){
                                btn_favorite.setLiked(false)
                                Toast.makeText(applicationContext,response.body()?.message,Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(applicationContext,response.body()?.message,Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<DefaultResponse?>, t: Throwable) {
                            Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
                        }
                    })
            }
        })

    }

}