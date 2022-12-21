package com.example.perusnia

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.perusnia.Model.*
import com.example.perusnia.Retrofit.RetrofitClient
import com.example.perusnia.adapter.TopRatedBook_Adapter
import com.example.perusnia.storage.SharedPrefManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_book.*
import kotlinx.android.synthetic.main.activity_book_detile.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.btn_search
import kotlinx.android.synthetic.main.activity_home.recyclerview
import kotlinx.android.synthetic.main.activity_home.refreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log


class HomeActivity : AppCompatActivity() {

    lateinit var topratedbookAdapter: TopRatedBook_Adapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sharedPrefManager = SharedPrefManager.getInstance(this).user
        val id_users = sharedPrefManager.id_users.toInt()

        RetrofitClient.instance.getLogUserRead(id_users)//---------------------- response
            .enqueue(object : Callback<singleBookResponse?> {
                override fun onResponse(
                    call: Call<singleBookResponse?>,
                    response: Response<singleBookResponse?>,
                ) {
                    if (response.body()?.status == 200){
                        val data = response.body()?.data!!
                        btn_log_user_read.visibility = View.VISIBLE
                        message_log_user_read.setText("Ini Buku terakhir yang anda baca!!, apakah sudah selesai di baca?")

                        btn_log_user_read.setOnClickListener(){
                            RetrofitClient.instance.getSpesificBook(10)//--------------------------- response 1
                                .enqueue(object : Callback<bookResponse?> {
                                    override fun onResponse(
                                        call: Call<bookResponse?>,
                                        response1: Response<bookResponse?>,
                                    ) {
                                        if (response1.isSuccessful){

                                            RetrofitClient.instance.getSpesificUser(id_users)//------------------- response 2
                                                .enqueue(object : Callback<userResponse?> {
                                                    override fun onResponse(
                                                        call: Call<userResponse?>,
                                                        response2: Response<userResponse?>,
                                                    ) {
                                                        val users = response2.body()!!.data

                                                        startActivity(
                                                            Intent(applicationContext,BookDetileActivity::class.java)
                                                                .putExtra("book", response1.body()?.data?.get(0))
                                                                .putExtra("users",users)
                                                        )
                                                    }

                                                    override fun onFailure(call: Call<userResponse?>, t: Throwable) {

                                                    }
                                                })


                                        }
                                    }

                                    override fun onFailure(
                                        call: Call<bookResponse?>,
                                        t: Throwable,
                                    ) {
                                        Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
                                    }
                                })
                        }

                        layout_book_detile.visibility = View.VISIBLE
                        Picasso.get()
                            .load("${RetrofitClient.BASE_URL}api/files.php?api_key=fasih123&file=${data.cover}")
                            .placeholder(R.drawable.default_image)
                            .error(R.drawable.default_image)
                            .into(image_log_user_read)
                        juduBook_log_user_read.setText(data.judul)
                        pengarang_log_user_read.setText(data.author)
                    }
                }

                override fun onFailure(call: Call<singleBookResponse?>, t: Throwable) {
                    Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
                }
            })



        //---------------navigation-----------------------------------

        bottom_navigation.selectedItemId = R.id.home

        // Perform item selected listener
        bottom_navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> return@OnNavigationItemSelectedListener true
                R.id.myBook -> {
                    startActivity(Intent(applicationContext, MyBookActivity::class.java))
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
        RetrofitClient.instance.getCartTotal(id_users)
            .enqueue(object : Callback<cartTotal_Response?> {
                override fun onResponse(
                    call: Call<cartTotal_Response?>,
                    response: Response<cartTotal_Response?>,
                ) {
                    if (response.body()?.status == 200){
                        cartItemBadge.visibility = View.VISIBLE
                        cartItemCount.text = response.body()!!.data!!.totalItem
                    }else{
                        cartItemBadge.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<cartTotal_Response?>, t: Throwable) {
                    cartItemBadge.visibility = View.GONE
                }
            })

        swipeRefreshLayout = refreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            // on below line we are setting is refreshing to false.
            overridePendingTransition(0, 0);
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
            swipeRefreshLayout.isRefreshing = false
        }

        setupRecylerView(id_users)
        getDataFromAPI()
    }

    override fun onStart() { // code ayng di jalankan di awal atau menggunakan init{}
        super.onStart()

        if (!SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }


    }

    private fun setupRecylerView(id_users:Int){
        topratedbookAdapter = TopRatedBook_Adapter(arrayListOf(),object : TopRatedBook_Adapter.OnAdapterListener{
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
                    if (response.body()?.status == 200){
                        showData(response.body()!!)
                    }else{
                        recyclerview.visibility = View.GONE
                        Toast.makeText(applicationContext,"data not found", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<TopRatedBook_Response?>, t: Throwable) {
                    recyclerview.visibility = View.GONE
                    Toast.makeText(applicationContext,"data not found", Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun showData(response: TopRatedBook_Response){
        val datas = response.data!!
        topratedbookAdapter.setData(datas)
    }

    @SuppressLint("RestrictedApi")
    fun showdialogSearch(){
        val inputForm = TextInputEditText(this)
        MaterialAlertDialogBuilder(this)
            .setTitle("Search a book")
            .setView(inputForm,70,0,70,0)
            .setPositiveButton("Search") { dialog, which ->
                val input = inputForm.text.toString()
                startActivity(
                    Intent(applicationContext,SerachResult_Activity::class.java)
                        .putExtra("keyword",input)
                )
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }


}