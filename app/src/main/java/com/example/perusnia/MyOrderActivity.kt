package com.example.perusnia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perusnia.Model.DataXXXXXX
import com.example.perusnia.Model.TransactionResponse
import com.example.perusnia.Model.bookResponse
import com.example.perusnia.Retrofit.RetrofitClient
import com.example.perusnia.adapter.CheckoutAdapter
import com.example.perusnia.adapter.MyOrderAdapter
import com.example.perusnia.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.activity_my_order.*
import kotlinx.android.synthetic.main.activity_my_order.btn_back
import kotlinx.android.synthetic.main.activity_my_order.recyclerview
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyOrderActivity : AppCompatActivity() {

    lateinit var myOrderAdapter: MyOrderAdapter
    val sharedPrefManager = SharedPrefManager.getInstance(this).user
    val id_users = sharedPrefManager.id_users.toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_order)

        btn_back.setOnClickListener(){
            finish()
        }


    }

    override fun onStart() { // code ayng di jalankan di awal atau menggunakan init{}
        super.onStart()

        setupRecylerView()
        getDataFromAPI()
    }

    private fun setupRecylerView(){
        myOrderAdapter = MyOrderAdapter(arrayListOf(),object : MyOrderAdapter.OnAdapterListener {
            override fun onClick(data: DataXXXXXX) {
                startActivity(
                    Intent(applicationContext,Detile_MyOrder_Activity::class.java)
                        .putExtra("data",data)
                )
            }
        })
        recyclerview.apply {
            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL,false)
            adapter = myOrderAdapter
        }
    }

    private fun getDataFromAPI(){
       RetrofitClient.instance.getUserTransaction(id_users)
           .enqueue(object : Callback<TransactionResponse?> {
               override fun onResponse(
                   call: Call<TransactionResponse?>,
                   response: Response<TransactionResponse?>,
               ) {
                   if (response.body()?.status == 200){
                       showData(response.body()!!)
                   }else{
                       recyclerview.visibility = View.GONE
                       Toast.makeText(applicationContext,"data not found", Toast.LENGTH_LONG).show()
                   }
               }

               override fun onFailure(call: Call<TransactionResponse?>, t: Throwable) {
                   recyclerview.visibility = View.GONE
                   Toast.makeText(applicationContext,"data not found", Toast.LENGTH_LONG).show()
               }
           })
    }
    private fun showData(response: TransactionResponse){
        val datas = response.data!!
        myOrderAdapter.setData(datas)
    }
}