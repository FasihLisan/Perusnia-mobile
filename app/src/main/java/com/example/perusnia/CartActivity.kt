package com.example.perusnia

import android.content.DialogInterface.OnShowListener
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perusnia.Model.*
import com.example.perusnia.Retrofit.RetrofitClient
import com.example.perusnia.adapter.CartAdapter
import com.example.perusnia.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_cart.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CartActivity : AppCompatActivity() {

    lateinit var cartAdapter: CartAdapter
    val sharedPrefManager = SharedPrefManager.getInstance(this).user
    val id_users = sharedPrefManager.id_users.toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        btn_back.setOnClickListener {
            finish()
        }

        RetrofitClient.instance.getCartTotal(id_users)
            .enqueue(object : Callback<cartTotal_Response?> {
                override fun onResponse(
                    call: Call<cartTotal_Response?>,
                    response: Response<cartTotal_Response?>,
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.data!!.totalHarga.isNullOrEmpty()) {
                            total_item.text = "TOTAL(0 ITEMS)"
                            total_harga.text = "Rp. "
                        } else {
                            total_item.text =
                                "TOTAL(${response.body()?.data!!.totalItem.toString()} ITEMS)"
                            total_harga.text =
                                if (response.body()?.data!!.totalHarga!!.toInt() != 0) "Rp." + NumberFormat.getNumberInstance(
                                    Locale.US)
                                    .format(response.body()?.data!!.totalHarga!!.toInt()) else "Free"
                        }
                    }else{
                        total_item.text = "TOTAL(0 ITEMS)"
                        total_harga.text = "Rp. "
                    }
                }

                override fun onFailure(call: Call<cartTotal_Response?>, t: Throwable) {
                    total_item.text = "TOTAL(0 ITEMS)"
                    total_harga.text = "Rp. "
                    Toast.makeText(applicationContext,t.message.toString(), Toast.LENGTH_LONG).show()
                }
            })

        btn_checkout.setOnClickListener(){
           RetrofitClient.instance.getSpesificCart(id_users)
               .enqueue(object : Callback<bookResponse?> {
                   override fun onResponse(
                       call: Call<bookResponse?>,
                       response: Response<bookResponse?>,
                   ) {
                       if (response.body()?.status == 200){
                           RetrofitClient.instance.getCartTotal(id_users)
                               .enqueue(object : Callback<cartTotal_Response?> {
                                   override fun onResponse(
                                       call: Call<cartTotal_Response?>,
                                       response1: Response<cartTotal_Response?>,
                                   ) {
                                       if (response1.body()?.data!!.totalHarga!!.toInt() > 0){
                                           RetrofitClient.instance.getSpesificUser(id_users)
                                               .enqueue(object : Callback<userResponse?> {
                                                   override fun onResponse(
                                                       call: Call<userResponse?>,
                                                       response2: Response<userResponse?>,
                                                   ) {
                                                       val users = response2.body()!!.data
                                                       startActivity(
                                                           Intent(applicationContext,CheckoutActivity::class.java)
                                                               .putExtra("totalHarga",response1.body()?.data!!.totalHarga)
                                                               .putExtra("users",users)
                                                       )
                                                   }

                                                   override fun onFailure(
                                                       call: Call<userResponse?>,
                                                       t: Throwable,
                                                   ) {
                                                       Toast.makeText(applicationContext,"Gratis!!", Toast.LENGTH_LONG).show()
                                                   }
                                               })
                                       }else{
                                           Toast.makeText(applicationContext,"Tidak ada item yang di checkout!!", Toast.LENGTH_LONG).show()
                                       }
                                   }

                                   override fun onFailure(
                                       call: Call<cartTotal_Response?>,
                                       t: Throwable,
                                   ) {
                                       Toast.makeText(applicationContext,t.message.toString(), Toast.LENGTH_LONG).show()
                                   }
                               })
                       }
                   }

                   override fun onFailure(call: Call<bookResponse?>, t: Throwable) {
                       Toast.makeText(applicationContext,"Tidak ada item untuk di checkout", Toast.LENGTH_LONG).show()
                   }
               })
        }
    }

    override fun onStart() { // code ayng di jalankan di awal atau menggunakan init{}
        super.onStart()

        setupRecylerView()
        getDataFromAPI()
    }
    private fun setupRecylerView(){
        cartAdapter = CartAdapter(arrayListOf(),object : CartAdapter.OnAdapterListener {
            override fun onClick(data: DataX) {
                startActivity(
                    Intent(applicationContext,BookDetileActivity::class.java)
                        .putExtra("book",data)
                )
            }

            override fun onClick2(data: DataX) {
                val builder = AlertDialog.Builder(this@CartActivity)
                builder.setMessage("Are you sure you want to Delete?")
                    .setCancelable(false)
                    .setPositiveButton("Delete") { dialog, id ->
                        RetrofitClient.instance.deleteCart(id_users,data.idBook!!.toInt())
                            .enqueue(object : Callback<DefaultResponse?> {
                                override fun onResponse(
                                    call: Call<DefaultResponse?>,
                                    response: Response<DefaultResponse?>,
                                ) {
                                    if (response.isSuccessful){
                                        Toast.makeText(applicationContext,response.body()!!.message, Toast.LENGTH_LONG).show()
                                        recreate()
                                    }else{
                                        Toast.makeText(applicationContext,response.body()!!.message, Toast.LENGTH_LONG).show()
                                    }
                                }

                                override fun onFailure(call: Call<DefaultResponse?>, t: Throwable) {
                                    Toast.makeText(applicationContext,t.message.toString(), Toast.LENGTH_LONG).show()
                                }
                            })

                    }
                    .setNegativeButton("Cencel") { dialog, id ->
                        dialog.dismiss()
                    }
                val alert = builder.create()

                alert.setOnShowListener(OnShowListener {
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.red))
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.grey1))
                })

                alert.show()
            }
        })
        recyclerview.apply {
            layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
            adapter = cartAdapter
        }
    }

    private fun getDataFromAPI(){
        RetrofitClient.instance.getSpesificCart(id_users)
            .enqueue(object : Callback<bookResponse?> {
                override fun onResponse(
                    call: Call<bookResponse?>,
                    response: Response<bookResponse?>,
                ) {
                    if (response.body()?.status == 200){
                        showData(response.body()!!)
                    }else{
                        recyclerview.visibility = View.GONE
                        Toast.makeText(applicationContext,"data not found", Toast.LENGTH_LONG).show()
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
        cartAdapter.setData(datas)
    }
}