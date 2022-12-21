package com.example.perusnia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perusnia.Model.DataXXXXXX
import com.example.perusnia.Model.DetaileTransactionResponse
import com.example.perusnia.Retrofit.RetrofitClient
import com.example.perusnia.adapter.Detile_MyOrder_Adapter
import com.example.perusnia.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_detile_my_order.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Detile_MyOrder_Activity : AppCompatActivity() {

    lateinit var detile_MyOrder_Adapter: Detile_MyOrder_Adapter
    val sharedPrefManager = SharedPrefManager.getInstance(this).user
    val id_users = sharedPrefManager.id_users.toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detile_my_order)

        val data = intent.getParcelableExtra<DataXXXXXX>("data")

        if (data != null) {
            orderId.text = data.orderId
            tipePembayran.text = data.paymentType
            va_number.text = data.vaNumber
            bank.text = data.bank
            gross_amount.text = data.grossAmount
            transaction_time.text = data.transactionTime
            transaction_status.text = data.transactionStatus
            settlement_time.text = data.settlementTime
        }

        btn_back.setOnClickListener(){
            finish()
        }


        setupRecylerView()
        getDataFromAPI(data?.transactionId.toString())
    }

    override fun onStart() { // code ayng di jalankan di awal atau menggunakan init{}
        super.onStart()


    }

    private fun setupRecylerView(){
        detile_MyOrder_Adapter = Detile_MyOrder_Adapter(arrayListOf())
        recyclerview.apply {
            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL,false)
            adapter = detile_MyOrder_Adapter
        }
    }

    private fun getDataFromAPI(transaction_id:String){
       RetrofitClient.instance.getDetileUserTransaction(transaction_id)
           .enqueue(object : Callback<DetaileTransactionResponse?> {
               override fun onResponse(
                   call: Call<DetaileTransactionResponse?>,
                   response: Response<DetaileTransactionResponse?>,
               ) {
                   if (response.body()?.status == 200){
                       showData(response.body()!!)
                   }else{
                       recyclerview.visibility = View.GONE
                       Toast.makeText(applicationContext,"data not found", Toast.LENGTH_LONG).show()
                   }
               }

               override fun onFailure(call: Call<DetaileTransactionResponse?>, t: Throwable) {
                   recyclerview.visibility = View.GONE
                   Toast.makeText(applicationContext,"data not found", Toast.LENGTH_LONG).show()
               }
           })
    }
    private fun showData(response: DetaileTransactionResponse){
        val datas = response.data!!
        detile_MyOrder_Adapter.setData(datas)
    }
}