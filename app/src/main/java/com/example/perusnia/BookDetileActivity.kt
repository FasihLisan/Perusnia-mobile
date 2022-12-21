package com.example.perusnia

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.perusnia.Model.*
import com.example.perusnia.Retrofit.RetrofitClient
import com.example.perusnia.adapter.BookRate_Adapter
import com.example.perusnia.storage.SharedPrefManager
import com.like.LikeButton
import com.like.OnLikeListener
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.BillingAddress
import com.midtrans.sdk.corekit.models.ItemDetails
import com.midtrans.sdk.corekit.models.ShippingAddress
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_book_detile.*
import kotlinx.android.synthetic.main.activity_book_detile.recyclerview
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log


class BookDetileActivity : AppCompatActivity() {

    lateinit var bookrateAdapter: BookRate_Adapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detile)

        val sharedPrefManager = SharedPrefManager.getInstance(this).user
        val id_users = sharedPrefManager.id_users.toInt()


        val book = intent.getParcelableExtra<DataX>("book")
        val users = intent.getParcelableExtra<Data>("users")

        if (book != null){
            Picasso.get()
                .load("${RetrofitClient.BASE_URL}api/files.php?api_key=fasih123&file=${book.cover}")
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(GambarBuku)
            Picasso.get()
                .load("${RetrofitClient.BASE_URL}api/files.php?api_key=fasih123&file=${book.cover}")
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(GambarBukuGradient)
            judulBuku.text = book.judul
            Pengarang.text = book.author
            TglUpload.text = SimpleDateFormat("dd MMMM yyyy").format(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(book.createdAt))
            RateBukuValue.text = if(book.rateBook.isNullOrEmpty()) "-/5" else "${book.rateBook}/5"
            pageBookValue.text = book.halaman
            sinopsisBuku.text = Html.fromHtml(Html.fromHtml(book.description).toString())
            price.text = if (book.harga!!.toInt() != 0) "Rp."+NumberFormat.getNumberInstance(Locale.US).format(book.harga!!.toInt()) else "Free"



            RetrofitClient.instance.getSpesificRateBook("fasih123",id_users,book.idBook!!.toInt())
                .enqueue(object : Callback<SpesificRateBook_Response?> {
                    override fun onResponse(
                        call: Call<SpesificRateBook_Response?>,
                        response: Response<SpesificRateBook_Response?>
                    ) {
                        if (response.body()?.status == 200){
                            if (response.body()?.data!!.rateScore != null){

                                feedback_layout.visibility = View.GONE
                                your_feedback_layout.visibility = View.VISIBLE

                                val data = response.body()?.data
                                Picasso.get()
                                    .load("${RetrofitClient.BASE_URL}api/files.php?api_key=fasih123&file=${data!!.foto}")
                                    .placeholder(R.drawable.default_image)
                                    .error(R.drawable.default_image)
                                    .into(foto_feedback)
                                name_feedback.text = data!!.namaLengkap
                                rating_feedback.rating = data!!.rateScore!!.toFloat()
                                tgl_feedback.text = SimpleDateFormat("dd MMMM yyyy").format(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data.createdAt))
                                feedback.text = data!!.comment
                            }else{
                                feedback_layout.visibility = View.VISIBLE
                                your_feedback_layout.visibility = View.GONE
                                btn_ratingBar.rating = 0.0f
                                txtFeedback.setText("")
                            }
                        }else{
                            feedback_layout.visibility = View.VISIBLE
                            your_feedback_layout.visibility = View.GONE
                            btn_ratingBar.rating = 0.0f
                            txtFeedback.setText("")
                        }
                    }

                    override fun onFailure(call: Call<SpesificRateBook_Response?>, t: Throwable) {
                        Log.d("API response",t.message.toString())
                    }
                })



            RetrofitClient.instance.check_mybook(id_users,book.idBook.toInt())
                .enqueue(object : Callback<DefaultResponse?> {
                    override fun onResponse(
                        call: Call<DefaultResponse?>,
                        response: Response<DefaultResponse?>,
                    ) {
                        if (response.body()?.status == 200){
                            btn_addCart.visibility = View.GONE
                            btn_buynNow.visibility = View.GONE
                            price.visibility = View.GONE

                            btn_readBook.visibility = View.VISIBLE
                        }else{
                            btn_addCart.visibility = View.VISIBLE
                            btn_buynNow.visibility = View.VISIBLE
                            price.visibility = View.VISIBLE

                            feedback_layout.visibility = View.GONE
                            your_feedback_layout.visibility = View.GONE
                            btn_readBook.visibility = View.GONE
                            if (price.text == "Free"){
                                btn_addCart.visibility = View.GONE
                                btn_buynNow.visibility = View.GONE
                                price.visibility = View.GONE

                                btn_readBook.visibility = View.VISIBLE

//                                feedback_layout.visibility = View.VISIBLE
//                                your_feedback_layout.visibility = View.GONE

                            }
                        }
                    }
                    override fun onFailure(call: Call<DefaultResponse?>, t: Throwable) {
                        Toast.makeText(applicationContext,t.message.toString(),Toast.LENGTH_LONG).show()
                    }
                })

            RetrofitClient.instance.getSpesificFavorite(id_users,book.idBook!!.toInt())
                .enqueue(object : Callback<DefaultResponse?> {
                    override fun onResponse(
                        call: Call<DefaultResponse?>,
                        response: Response<DefaultResponse?>
                    ) {
                        if (response.body()?.status == 200){
                            btn_favorite.setLiked(true)
                        }
                    }

                    override fun onFailure(call: Call<DefaultResponse?>, t: Throwable) {
                        Toast.makeText(applicationContext,t.message.toString(),Toast.LENGTH_LONG).show()
                    }
                })

        }




        btn_back.setOnClickListener{
           finish()
        }


        btn_send_feedback.setOnClickListener {
            val rating = btn_ratingBar.rating
            val comment = txtFeedback.text.toString()
            val id_users = id_users
            val id_book = book!!.idBook

            if (rating != null){
                RetrofitClient.instance.insertFeedback(rating,comment,id_book!!.toInt(),id_users)
                    .enqueue(object : Callback<DefaultResponse?> {
                        override fun onResponse(
                            call: Call<DefaultResponse?>,
                            response: Response<DefaultResponse?>
                        ) {
                            if (response.body()?.status == 200){
                                overridePendingTransition(0, 0);
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);
                                Toast.makeText(applicationContext,response.body()?.message,Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(applicationContext,response.body()?.message,Toast.LENGTH_LONG).show()
                            }

                        }

                        override fun onFailure(call: Call<DefaultResponse?>, t: Throwable) {
                            Toast.makeText(applicationContext,t.message.toString(),Toast.LENGTH_LONG).show()
                        }
                    })
            }else{
                Toast.makeText(applicationContext,"Rating is required",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
        }

        btn_edit_feedback.setOnClickListener(){
            feedback_layout.visibility = View.VISIBLE
            your_feedback_layout.visibility = View.GONE
            btn_send_feedback.visibility = View.GONE

            btn_cencel.visibility = View.VISIBLE
            btn_update_feedback.visibility = View.VISIBLE

            RetrofitClient.instance.getSpesificRateBook("fasih123",id_users.toInt(),book?.idBook!!.toInt())
                .enqueue(object : Callback<SpesificRateBook_Response?> {
                    override fun onResponse(
                        call: Call<SpesificRateBook_Response?>,
                        response: Response<SpesificRateBook_Response?>
                    ) {
                        val data = response.body()?.data
                        if (data?.rateScore != null){
                            btn_ratingBar.rating = data!!.rateScore!!.toFloat()
                        }
                        if (data?.comment != null){
                            txtFeedback.setText(data.comment)
                        }
                    }

                    override fun onFailure(call: Call<SpesificRateBook_Response?>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
        }

        btn_update_feedback.setOnClickListener(){
            val id_book = book?.idBook!!.toInt()
            val id_users = id_users
            val rating = btn_ratingBar.rating
            val comment = txtFeedback.text.toString()
            Log.d("TESITNG","${id_book} ${id_users} ${rating} ${comment} ${id_book} ${id_users}")
            RetrofitClient.instance.updateFeedback(id_book,id_users,rating,comment)
                .enqueue(object : Callback<DefaultResponse?> {
                    override fun onResponse(
                        call: Call<DefaultResponse?>,
                        response: Response<DefaultResponse?>
                    ) {
                        if (response.body()?.status == 200){
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                            Toast.makeText(applicationContext,response.body()?.message,Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(applicationContext,response.body()?.message,Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<DefaultResponse?>, t: Throwable) {
                        Toast.makeText(applicationContext,t.message.toString(),Toast.LENGTH_LONG).show()
                    }
                })
        }

        btn_cencel.setOnClickListener(){
            feedback_layout.visibility = View.GONE
            your_feedback_layout.visibility = View.VISIBLE
        }

        btn_option_your_rate.setOnClickListener(){
            val builder = AlertDialog.Builder(this@BookDetileActivity)
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    RetrofitClient.instance.deleteFeedback(book?.idBook!!.toInt(),id_users)
                        .enqueue(object : Callback<DefaultResponse?> {
                            override fun onResponse(
                                call: Call<DefaultResponse?>,
                                response: Response<DefaultResponse?>
                            ) {
                                if (response.body()?.status == 200){
                                    overridePendingTransition(0, 0);
                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(getIntent());
                                    overridePendingTransition(0, 0);
                                    Toast.makeText(applicationContext,response.body()?.message,Toast.LENGTH_LONG).show()
                                }else{
                                    Toast.makeText(applicationContext,response.body()?.message,Toast.LENGTH_LONG).show()
                                }
                            }

                            override fun onFailure(call: Call<DefaultResponse?>, t: Throwable) {
                                Toast.makeText(applicationContext,t.message.toString(),Toast.LENGTH_LONG).show()
                            }
                        })
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }



        btn_favorite.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                RetrofitClient.instance.insertFavorite(id_users,book?.idBook!!.toInt())
                    .enqueue(object : Callback<DefaultResponse?> {
                        override fun onResponse(
                            call: Call<DefaultResponse?>,
                            response: Response<DefaultResponse?>
                        ) {
                            if (response.body()?.status!!.equals(200)){
                                btn_favorite.setLiked(true)
                                Toast.makeText(applicationContext,response.body()?.message,Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(applicationContext,response.body()?.message,Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<DefaultResponse?>, t: Throwable) {
                            Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
                        }
                    })
            }


            override fun unLiked(likeButton: LikeButton?) {
                RetrofitClient.instance.deleteFavorite(id_users,book?.idBook!!.toInt())
                    .enqueue(object : Callback<DefaultResponse?> {
                        override fun onResponse(
                            call: Call<DefaultResponse?>,
                            response: Response<DefaultResponse?>
                        ) {
                            if (response.body()?.status!!.equals(200)){
                                btn_favorite.setLiked(false)
                                Toast.makeText(applicationContext,response.body()?.message,Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(applicationContext,response.body()?.message,Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<DefaultResponse?>, t: Throwable) {
                            Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
                        }
                    })
            }
        })

       btn_readBook.setOnClickListener {
           val intent = Intent(applicationContext,PdfViewActivity::class.java)

           RetrofitClient.instance.insertOrUpdateLogUserRead(id_users,book!!.idBook!!.toInt())
               .enqueue(object : Callback<DefaultResponse?> {
                   override fun onResponse(
                       call: Call<DefaultResponse?>,
                       response: Response<DefaultResponse?>,
                   ) {
                       if (response.isSuccessful){
                           Log.d("Log_user_read",response.body()!!.message);
                       }
                   }

                   override fun onFailure(call: Call<DefaultResponse?>, t: Throwable) {
                       Log.d("Log_user_read",t.message.toString());
                   }
               })

           intent.putExtra("pdf_url",book!!.fileBuku)
           intent.putExtra("pdf_title",book!!.judul)

           startActivity(intent)
       }

        btn_addCart.setOnClickListener(){
            RetrofitClient.instance.insertCart(id_users,book?.idBook!!.toInt())
                .enqueue(object : Callback<DefaultResponse?> {
                    override fun onResponse(
                        call: Call<DefaultResponse?>,
                        response: Response<DefaultResponse?>,
                    ) {
                        if (response.isSuccessful){
                            Toast.makeText(applicationContext,"Success add to cart",Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(applicationContext,"Book alredy in cart",Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<DefaultResponse?>, t: Throwable) {
                        Toast.makeText(applicationContext,t.message.toString(),Toast.LENGTH_LONG).show()
                    }
                })
        }

        //midtrans----------------------------------->
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
                                insertDetileTransaction(id_transaction,id_users,book?.idBook!!.toInt())
                                Toast.makeText(this@BookDetileActivity, "Transaction Finished. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show()
                            }
                            TransactionResult.STATUS_PENDING -> {
                                insertDetileTransaction(id_transaction,id_users,book?.idBook!!.toInt())
                                Toast.makeText(this@BookDetileActivity, "Transaction Pending. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show()
                            }
                            TransactionResult.STATUS_FAILED ->
                                Toast.makeText(this@BookDetileActivity,
                                    "Transaction Failed. ID: " + result.getResponse().getTransactionId().toString() + ". Message: " + result.getResponse().getStatusMessage(), Toast.LENGTH_LONG).show()
                        }

                        result.getResponse().getValidationMessages()
                    } else if (result.isTransactionCanceled()) {
                        Toast.makeText(this@BookDetileActivity, "Transaction Canceled", Toast.LENGTH_LONG).show()
                    } else {
                        if (result.getStatus().equals(TransactionResult.STATUS_INVALID)) {
                            Toast.makeText(this@BookDetileActivity, "Transaction Invalid", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this@BookDetileActivity, "Transaction Finished with failure.", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
            .setMerchantBaseUrl("${RetrofitClient.BASE_URL}api/midtrans.php/")
            .enableLog(true)
            .setColorTheme(CustomColorTheme("#0ABF04", "#078C03", "#0ABF04"))
            .setLanguage("id")
            .buildSDK()


        btn_buynNow.setOnClickListener(){
            val transction_id = "PERUSNIA-"+ UUID.randomUUID().toString()

            val transactionRequest = TransactionRequest(transction_id, book?.harga!!.toDouble())

            val itemDetailsList: ArrayList<ItemDetails> = ArrayList()

            val detile = com.midtrans.sdk.corekit.models.ItemDetails(book.idBook,book.harga.toDouble(),1,book.judul)
            itemDetailsList.add(detile);
            transactionRequest.itemDetails = itemDetailsList;

            if (users != null){
                uikitDetailes(transactionRequest,users.username.toString(),users.noTelp.toString(),users.namaDepan.toString(),users.namaBelakang.toString(),users.email.toString(),users.alamat.toString(),users.kota.toString())
            }

            MidtransSDK.getInstance().transactionRequest = transactionRequest
            MidtransSDK.getInstance().startPaymentUiFlow(this)

        }

        setupRecylerView()
        getDataFromAPI(book?.idBook!!.toInt())

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




    }

    override fun onStart() {
        super.onStart()

    }


    private fun setupRecylerView(){
        bookrateAdapter = BookRate_Adapter(arrayListOf())
        recyclerview.apply {
            layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
            adapter = bookrateAdapter
        }
    }

    private fun  getDataFromAPI(id_book:Int){
        RetrofitClient.instance.getAllRateBook(id_book)
            .enqueue(object : Callback<BookRate_Response?> {
                override fun onResponse(
                    call: Call<BookRate_Response?>,
                    response: Response<BookRate_Response?>
                ) {
                    if (response.isSuccessful){
                        if (response.body()?.status == 200){
                            showData(response.body()!!)
                        }else{
                            recyclerview.visibility = View.GONE
                        }
                    }
                }

                override fun onFailure(call: Call<BookRate_Response?>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun showData(response: BookRate_Response){
        val datas = response.data!!
        bookrateAdapter.setData(datas)
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

    fun insertDetileTransaction(id_transaction:String,id_users:Int,id_book: Int){
        RetrofitClient.instance.insertDetaileTransaction(id_transaction,id_users,id_book)
            .enqueue(object : Callback<DefaultResponse?> {
                override fun onResponse(
                    call: Call<DefaultResponse?>,
                    response1: Response<DefaultResponse?>,
                ) {
                    Log.d("insert Detail",
                        id_transaction+" ${id_users} "+id_book+" ${response1.body()!!.message}")
                }

                override fun onFailure(
                    call: Call<DefaultResponse?>,
                    t: Throwable,
                ) {
                    Log.d("insert Detail",t.message.toString())
                }
            })
    }

}