package com.example.perusnia

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perusnia.Model.*
import com.example.perusnia.Retrofit.RetrofitClient
import com.example.perusnia.adapter.CheckoutAdapter
import com.example.perusnia.storage.SharedPrefManager
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.BillingAddress
import com.midtrans.sdk.corekit.models.ShippingAddress
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import kotlinx.android.synthetic.main.activity_checkout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*


class CheckoutActivity : AppCompatActivity() {

    lateinit var checkoutAdapter: CheckoutAdapter
    val sharedPrefManager = SharedPrefManager.getInstance(this).user
    val id_users = sharedPrefManager.id_users.toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val totalHarga = intent.getStringExtra("totalHarga")
        val users = intent.getParcelableExtra<Data>("users")

        btn_back.setOnClickListener(){
            finish()
        }

        RetrofitClient.instance.getSpesificUser(id_users)
            .enqueue(object : Callback<userResponse?> {
                override fun onResponse(
                    call: Call<userResponse?>,
                    response: Response<userResponse?>,
                ) {
                    if (response.isSuccessful){

                        val data =response.body()?.data
                        email.setText(data!!.email)
                        fisrtname.setText(data!!.namaDepan)
                        lastname.setText(data.namaBelakang)
                        phone.setText(data.noTelp)
                    }
                }

                override fun onFailure(call: Call<userResponse?>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })


        RetrofitClient.instance.getCartTotal(id_users)
            .enqueue(object : Callback<cartTotal_Response?> {
                override fun onResponse(
                    call: Call<cartTotal_Response?>,
                    response: Response<cartTotal_Response?>,
                ) {
                    if (response.body()?.status == 200){

                        val totalHarga = response.body()?.data!!.totalHarga!!.toInt()
                        val totalDiskon = 0

                        subtotal_item.text = if (totalHarga != 0) "Rp."+ NumberFormat.getNumberInstance(
                            Locale.US).format(totalHarga) else "Free"
                        subtotal_diskon.text = 0.toString()

                        val totalitem = response.body()!!.data!!.totalItem.toString()

                        total_item.text = "TOTAL(${totalitem} ITEMS)"

                        val totalPembayran = NumberFormat.getNumberInstance(
                            Locale.US).format( totalHarga.toInt()+totalDiskon.toInt())

                        total_pembayran.text = "Rp.${totalPembayran}"
                        total_harga.text = "Rp.${totalPembayran}"


                    }else{
                        total_item.text = "TOTAL(0 ITEMS)"
                        total_harga.text = "Rp. "
                    }
                }

                override fun onFailure(call: Call<cartTotal_Response?>, t: Throwable) {
                    Toast.makeText(applicationContext,t.message.toString(), Toast.LENGTH_LONG).show()
                }
            })

        SdkUIFlowBuilder.init()
            .setClientKey("SB-Mid-client-iIMWCUVAqp4mewuM")
            .setContext(applicationContext)
            .setTransactionFinishedCallback(object : TransactionFinishedCallback {
                override fun onTransactionFinished(result: TransactionResult?) {
                    if (result!!.getResponse() != null) {

                        val id_transaction = result.response.transactionId.toString()
                        val id_users = id_users

                        when (result.getStatus()) {
                            TransactionResult.STATUS_SUCCESS -> {
                                insertDetileTransaction(id_transaction,id_users)
                                deleteCartItem(id_users)
                                Toast.makeText(this@CheckoutActivity, "Transaction Finished. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show()
                            }
                            TransactionResult.STATUS_PENDING -> {
                                insertDetileTransaction(id_transaction,id_users)
                                deleteCartItem(id_users)
                                Toast.makeText(this@CheckoutActivity, "Transaction Pending. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show()
                            }
                            TransactionResult.STATUS_FAILED ->
                                Toast.makeText(this@CheckoutActivity,
                                "Transaction Failed. ID: " + result.getResponse().getTransactionId().toString() + ". Message: " + result.getResponse().getStatusMessage(), Toast.LENGTH_LONG).show()
                        }

                    result.getResponse().getValidationMessages()
                    } else if (result.isTransactionCanceled()) {
                        Toast.makeText(this@CheckoutActivity, "Transaction Canceled", Toast.LENGTH_LONG).show()
                    } else {
                        if (result.getStatus().equals(TransactionResult.STATUS_INVALID)) {
                            Toast.makeText(this@CheckoutActivity, "Transaction Invalid", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this@CheckoutActivity, "Transaction Finished with failure.", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
            .setMerchantBaseUrl("${RetrofitClient.BASE_URL}api/midtrans.php/")
            .enableLog(true)
            .setColorTheme(CustomColorTheme("#0ABF04", "#078C03", "#0ABF04"))
            .setLanguage("id")
            .buildSDK()


        btn_bayar.setOnClickListener(){

            val transction_id = "PERUSNIA-"+UUID.randomUUID().toString()

            val transactionRequest = TransactionRequest(transction_id, totalHarga!!.toDouble())

            val itemDetailsList: ArrayList<com.midtrans.sdk.corekit.models.ItemDetails> = ArrayList()

            RetrofitClient.instance.getSpesificCart(id_users)
                .enqueue(object : Callback<bookResponse?> {
                    override fun onResponse(
                        call: Call<bookResponse?>,
                        response: Response<bookResponse?>,
                    ) {
                        if (response.isSuccessful){
                            val data = response.body()?.data
                            if (data != null) {
                                for (i in data.indices){
                                    val detile = com.midtrans.sdk.corekit.models.ItemDetails(
                                        data[i].idBook,
                                        data[i].harga.toString().toDouble(),
                                        1,
                                        data[i].judul,

                                    )
                                    itemDetailsList.add(detile);
                                }

                            }else{
                                Toast.makeText(applicationContext,response.body()?.message, Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<bookResponse?>,
                        t: Throwable,
                    ) {
                        Toast.makeText(applicationContext,t.message.toString(), Toast.LENGTH_LONG).show()
                    }
                })

            transactionRequest.itemDetails = itemDetailsList;


            if (users != null){
                uikitDetailes(transactionRequest,users.username.toString(),users.noTelp.toString(),users.namaDepan.toString(),users.namaBelakang.toString(),users.email.toString(),users.alamat.toString(),users.kota.toString())
            }



            MidtransSDK.getInstance().transactionRequest = transactionRequest
            MidtransSDK.getInstance().startPaymentUiFlow(this)
        }



    }
    override fun onStart() { // code ayng di jalankan di awal atau menggunakan init{}
        super.onStart()

        setupRecylerView()
        getDataFromAPI()
    }

    private fun setupRecylerView(){
        checkoutAdapter = CheckoutAdapter(arrayListOf())
        recyclerview.apply {
            layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
            adapter = checkoutAdapter
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
        checkoutAdapter.setData(datas)
    }

    fun uikitDetailes(transactionRequest: TransactionRequest,username:String,noTelp:String,namaDepan:String,namaBelakang:String,email:String,alamat:String,kota:String){
        val customerDetails = com.midtrans.sdk.corekit.models.CustomerDetails()
        customerDetails.customerIdentifier = username
        customerDetails.phone = noTelp
        customerDetails.firstName = namaDepan
        customerDetails.lastName = namaBelakang
        customerDetails.email = email

        val shippingAddress = ShippingAddress()
        shippingAddress.address = alamat
        shippingAddress.city = kota
        shippingAddress.postalCode = "-"
        customerDetails.shippingAddress = shippingAddress

        val billingAddress = BillingAddress()
        billingAddress.address = alamat
        billingAddress.city = kota
        billingAddress.postalCode = "-"
        customerDetails.billingAddress = billingAddress

        transactionRequest.customerDetails = customerDetails;


    }

    fun insertDetileTransaction(id_transaction:String,id_users:Int){
        RetrofitClient.instance.getSpesificCart(id_users)
            .enqueue(object : Callback<bookResponse?> {
                override fun onResponse(
                    call: Call<bookResponse?>,
                    response: Response<bookResponse?>,
                ) {
                    if (response.isSuccessful){
                        val data = response.body()?.data
                        if (data != null) {
                            for (i in data.indices){
                               RetrofitClient.instance.insertDetaileTransaction(id_transaction,id_users,data[i].idBook!!.toInt())
                                   .enqueue(object : Callback<DefaultResponse?> {
                                       override fun onResponse(
                                           call: Call<DefaultResponse?>,
                                           response1: Response<DefaultResponse?>,
                                       ) {
                                           Log.d("insert Detail",
                                               id_transaction+" ${id_users} "+data[i].idBook!!.toString()+" ${response1.body()!!.message}")
                                       }

                                       override fun onFailure(
                                           call: Call<DefaultResponse?>,
                                           t: Throwable,
                                       ) {
                                           Log.d("insert Detail",t.message.toString())
                                       }
                                   })
                            }

                        }else{
                            Toast.makeText(applicationContext,response.body()?.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
                override fun onFailure(
                    call: Call<bookResponse?>,
                    t: Throwable,
                ) {
                    Toast.makeText(applicationContext,t.message.toString(), Toast.LENGTH_LONG).show()
                }
            })
    }
    fun deleteCartItem(id_users: Int){
        RetrofitClient.instance.deleteAllCartUsers(id_users)
            .enqueue(object : Callback<cartTotal_Response?> {
                override fun onResponse(
                    call: Call<cartTotal_Response?>,
                    response: Response<cartTotal_Response?>,
                ) {
                    Log.d("delete cart","Sucesss")
                }

                override fun onFailure(call: Call<cartTotal_Response?>, t: Throwable) {
                    Log.d("delete cart",t.message.toString())
                }
            })
    }

}