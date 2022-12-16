package com.example.perusnia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perusnia.Model.DataX
import com.example.perusnia.Model.bookResponse
import com.example.perusnia.Retrofit.RetrofitClient
import com.example.perusnia.adapter.BookAdapter
import com.example.perusnia.adapter.SerachResult_Adapter
import com.example.perusnia.adapter.TopRatedBook_Adapter
import com.example.perusnia.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_home.recyclerview
import kotlinx.android.synthetic.main.activity_serach_result.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SerachResult_Activity : AppCompatActivity() {

    lateinit var searchResult_Adapter: SerachResult_Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serach_result)

        var key = intent.getStringExtra("keyword")

        setupRecylerView()
        if (key != null) {
            getDataFromAPI(key)
        }else{
            recyclerview.visibility = View.GONE
            resultNotFound.visibility = View.VISIBLE
            resultNotFound.text = "Result of ${keyword} Not Found"
        }

        btn_back.setOnClickListener(){
            startActivity(Intent(applicationContext,HomeActivity::class.java))
        }

        keyword.setText(key)

        btn_search.setOnClickListener(){
            val key = keyword.text.toString()
            startActivity(
                Intent(this@SerachResult_Activity,SerachResult_Activity::class.java)
                    .putExtra("keyword",key)
            )
        }
    }

    private fun setupRecylerView(){
        searchResult_Adapter = SerachResult_Adapter(arrayListOf(),object : SerachResult_Adapter.OnAdapterListener{
            override fun onClick(data: DataX) {
                startActivity(
                    Intent(applicationContext,BookDetileActivity::class.java)
                        .putExtra("book",data)
                )
            }
        })
        recyclerview.apply {
            layoutManager = GridLayoutManager(applicationContext, 3)
            adapter = searchResult_Adapter
        }
    }

    private fun getDataFromAPI(keyword:String){
       RetrofitClient.instance.serachBook(keyword)
           .enqueue(object : Callback<bookResponse?> {
               override fun onResponse(
                   call: Call<bookResponse?>,
                   response: Response<bookResponse?>,
               ) {
                   if (response.isSuccessful){
                       showData(response.body()!!)
                   }else{
                       recyclerview.visibility = View.GONE
                       resultNotFound.visibility = View.VISIBLE
                       resultNotFound.text = "Result of ${keyword} Not Found"
                   }
               }

               override fun onFailure(call: Call<bookResponse?>, t: Throwable) {
                   recyclerview.visibility = View.GONE
                   resultNotFound.visibility = View.VISIBLE
                   resultNotFound.text = "Result of ${keyword} Not Found"
               }
           })
    }

    private fun showData(response: bookResponse){
        val datas = response.data!!
        searchResult_Adapter.setData(datas)
    }
}