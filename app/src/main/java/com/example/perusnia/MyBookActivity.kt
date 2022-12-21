package com.example.perusnia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perusnia.Model.DataX
import com.example.perusnia.Model.bookResponse
import com.example.perusnia.Retrofit.RetrofitClient
import com.example.perusnia.adapter.MyBookAdapter
import com.example.perusnia.storage.SharedPrefManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_my_book.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyBookActivity : AppCompatActivity() {

    lateinit var myBookAdapter: MyBookAdapter
    val sharedPrefManager = SharedPrefManager.getInstance(this).user
    val id_users = sharedPrefManager.id_users.toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_book)


        btn_search.setOnClickListener(){
            val key = keyword.text.toString()
            startActivity(
                Intent(applicationContext,SerachResult_Activity::class.java)
                    .putExtra("keyword",key)
            )
        }

        //---------------navigation-----------------------------------
        bottom_navigation.selectedItemId = R.id.myBook

        // Perform item selected listener
        bottom_navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.myBook -> return@OnNavigationItemSelectedListener true
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

    override fun onStart() { // code ayng di jalankan di awal atau menggunakan init{}
        super.onStart()

        setupRecylerView()
        getDataFromAPI()
    }

    private fun setupRecylerView(){
        myBookAdapter = MyBookAdapter(arrayListOf(),object : MyBookAdapter.OnAdapterListener{
            override fun onClick(data: DataX) {
                startActivity(
                    Intent(applicationContext,BookDetileActivity::class.java)
                        .putExtra("book",data)
                )
            }
        })
        recyclerview.apply {
            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL,false)
            adapter = myBookAdapter
        }
    }

    private fun getDataFromAPI(){
       RetrofitClient.instance.getAllMyBookUsers(id_users)
           .enqueue(object : Callback<bookResponse?> {
               override fun onResponse(
                   call: Call<bookResponse?>,
                   response: Response<bookResponse?>,
               ) {
                   if (response.body()?.data.isNullOrEmpty()) {
                       recyclerview.visibility = View.GONE
                       Toast.makeText(applicationContext, "data not found", Toast.LENGTH_LONG)
                           .show()
                   } else {
                       showData(response.body()!!)
                   }

               }
               override fun onFailure(call: Call<bookResponse?>, t: Throwable) {
                   recyclerview.visibility = View.GONE
                   Toast.makeText(applicationContext,"data not found", Toast.LENGTH_LONG).show()
               }
           })
    }

    private fun showData(response: bookResponse){
        val datas = response.data!!
        myBookAdapter.setData(datas)
    }
}