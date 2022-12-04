package com.example.perusnia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.perusnia.Model.DataX
import com.example.perusnia.Model.bookResponse
import com.example.perusnia.Retrofit.RetrofitClient
import com.example.perusnia.adapter.BookAdapter
import kotlinx.android.synthetic.main.activity_book.*
import kotlinx.android.synthetic.main.activity_book.recyclerview
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookActivity : AppCompatActivity() {

    lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        btn_back.setOnClickListener {
            finish()
        }
    }

    override fun onStart() { // code ayng di jalankan di awal atau menggunakan init{}
        super.onStart()

        setupRecylerView()
        getDataFromAPI()
    }

    private fun setupRecylerView(){
        bookAdapter = BookAdapter(arrayListOf(),object : BookAdapter.OnAdapterListener{
            override fun onClick(data: DataX) {
                startActivity(
                    Intent(applicationContext,BookDetileActivity::class.java)
                        .putExtra("book",data)
                )
            }
        })
        recyclerview.apply {
            layoutManager = GridLayoutManager(applicationContext, 3)
            adapter = bookAdapter
        }
    }

    private fun getDataFromAPI(){
        RetrofitClient.instance.getBook()
            .enqueue(object : Callback<bookResponse?> {
                override fun onResponse(
                    call: Call<bookResponse?>,
                    response: Response<bookResponse?>
                ) {
                    if (response.isSuccessful){
                        showData(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<bookResponse?>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun showData(response: bookResponse){
        val datas = response.data!!
        bookAdapter.setData(datas)
//        for (data in datas){
//            Log.d("TES API","judul: ${data.judul}")
//        }
    }

}