package com.example.perusnia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.perusnia.Model.DataX
import com.example.perusnia.Model.bookResponse
import com.example.perusnia.Model.userResponse
import com.example.perusnia.Retrofit.RetrofitClient
import com.example.perusnia.adapter.BookAdapter
import com.example.perusnia.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_book.*
import kotlinx.android.synthetic.main.activity_book.btn_back
import kotlinx.android.synthetic.main.activity_book.btn_search
import kotlinx.android.synthetic.main.activity_book.recyclerview
import kotlinx.android.synthetic.main.activity_favorite.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookActivity : AppCompatActivity() {

    lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        val sharedPrefManager = SharedPrefManager.getInstance(this).user
        val id_users = sharedPrefManager.id_users.toInt()

        btn_back.setOnClickListener {
            finish()
        }

        btn_search.setOnClickListener(){
            val key = keyword.text.toString()
            startActivity(
                Intent(applicationContext,SerachResult_Activity::class.java)
                    .putExtra("keyword",key)
            )
        }

        setupRecylerView(id_users)
        getDataFromAPI()
    }



    override fun onStart() { // code ayng di jalankan di awal atau menggunakan init{}
        super.onStart()


    }

    private fun setupRecylerView(id_users:Int){
        bookAdapter = BookAdapter(arrayListOf(),object : BookAdapter.OnAdapterListener{
            override fun onClick(data: DataX) {
                RetrofitClient.instance.getSpesificUser(id_users)
                    .enqueue(object : Callback<userResponse?> {
                        override fun onResponse(
                            call: Call<userResponse?>,
                            response: Response<userResponse?>,
                        ) {
                            val users = response.body()!!.data
                            startActivity(
                                Intent(applicationContext,BookDetileActivity::class.java)
                                    .putExtra("book",data)
                                    .putExtra("users",users)
                            )
                        }

                        override fun onFailure(call: Call<userResponse?>, t: Throwable) {

                        }
                    })
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
                    recyclerview.visibility = View.GONE
                    Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun showData(response: bookResponse){
        val datas = response.data!!
        bookAdapter.setData(datas)
    }

}