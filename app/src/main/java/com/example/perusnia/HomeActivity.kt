package com.example.perusnia

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perusnia.Model.DataX
import com.example.perusnia.Model.TopRatedBook_Response
import com.example.perusnia.Model.userResponse
import com.example.perusnia.Retrofit.RetrofitClient
import com.example.perusnia.adapter.TopRatedBook_Adapter
import com.example.perusnia.storage.SharedPrefManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeActivity : AppCompatActivity() {

    lateinit var topratedbookAdapter: TopRatedBook_Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sharedPrefManager = SharedPrefManager.getInstance(this).user
        val id_users = sharedPrefManager.id_users.toInt()




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

        RetrofitClient.instance.getSpesificUser(id_users)
            .enqueue(object : Callback<userResponse?> {
                override fun onResponse(
                    call: Call<userResponse?>,
                    response: Response<userResponse?>
                ) {
                    val data = response.body()?.data
                    if (data?.username != null){
                        usernameHello.text = "Hello ${data?.username}"
                    }else{
                        usernameHello.text = "Hello Anonymous"
                    }
                }

                override fun onFailure(call: Call<userResponse?>, t: Throwable) {
                    Log.d("TAG",t.message.toString())
                }
            })

        btn_ViewMore.setOnClickListener{
            val intent = Intent(this@HomeActivity, BookActivity::class.java)
            startActivity(intent)
        }
        btn_cart.setOnClickListener{
            val intent = Intent(this@HomeActivity, CartActivity::class.java)
            startActivity(intent)
        }
        btn_search.setOnClickListener(){
            showdialogSearch()
        }
    }

    override fun onStart() { // code ayng di jalankan di awal atau menggunakan init{}
        super.onStart()

        if (!SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }

        setupRecylerView()
        getDataFromAPI()
    }

    private fun setupRecylerView(){
        topratedbookAdapter = TopRatedBook_Adapter(arrayListOf(),object : TopRatedBook_Adapter.OnAdapterListener{
            override fun onClick(data: DataX) {
               startActivity(
                   Intent(applicationContext,BookDetileActivity::class.java)
                       .putExtra("book",data)
               )
            }

        })
        recyclerview.apply {
            layoutManager = LinearLayoutManager(applicationContext,RecyclerView.HORIZONTAL,false)
            adapter = topratedbookAdapter
        }
    }

    private fun getDataFromAPI(){
        RetrofitClient.instance.getTopRateBook()
            .enqueue(object : Callback<TopRatedBook_Response?> {
                override fun onResponse(
                    call: Call<TopRatedBook_Response?>,
                    response: Response<TopRatedBook_Response?>
                ) {
                    if (response.isSuccessful){
                        showData(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<TopRatedBook_Response?>, t: Throwable) {
                    Toast.makeText(applicationContext,t.message.toString(),Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun showData(response: TopRatedBook_Response){
        val datas = response.data!!
        topratedbookAdapter.setData(datas)
//        for (data in datas){
//            Log.d("TES API","judul: ${data.judul}")
//        }
    }

    @SuppressLint("RestrictedApi")
    fun showdialogSearch(){
        val inputForm = TextInputEditText(this)
        MaterialAlertDialogBuilder(this)
            .setTitle("Search a book")
            .setView(inputForm,70,0,70,0)
            .setPositiveButton("Search") { dialog, which ->
                val input = inputForm.text.toString()
                Toast.makeText(applicationContext,"Value is $input",Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }


}