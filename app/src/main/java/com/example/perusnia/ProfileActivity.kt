package com.example.perusnia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.perusnia.Model.userResponse
import com.example.perusnia.Retrofit.RetrofitClient
import com.example.perusnia.storage.SharedPrefManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val sharedPrefManager = SharedPrefManager.getInstance(this).user
        val id_users = sharedPrefManager.id_users.toInt()


        RetrofitClient.instance.getSpesificUser(id_users)
            .enqueue(object : Callback<userResponse?> {
                override fun onResponse(
                    call: Call<userResponse?>,
                    response: Response<userResponse?>
                ) {

                    try {
                        Picasso.get()
                            .load("${RetrofitClient.BASE_URL}api/files.php?api_key=fasih123&file=${response.body()?.data!!.foto}")
                            .placeholder(R.drawable.default_image)
                            .error(R.drawable.default_image)
                            .into(profile)
                    }catch (e: Exception){
                        Log.i("Picasso:","Message => "+e);
                    }

                    accountName.text = response.body()?.data!!.namaDepan+" "+response.body()?.data!!.namaBelakang

                }

                override fun onFailure(call: Call<userResponse?>, t: Throwable) {
                    Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show();
                }
            })



        btn_accountSetting.setOnClickListener(){
            startActivity(Intent(applicationContext,AccountSetting::class.java))
        }

        btn_logout.setOnClickListener(){
            val sharedPrefManager = SharedPrefManager.getInstance(this)
            sharedPrefManager.clear()

            startActivity(Intent(applicationContext,MainActivity::class.java))
        }

        btn_favorite.setOnClickListener(){
            startActivity(Intent(applicationContext,FavoriteActivity::class.java))
        }

        btn_myOrder.setOnClickListener(){
            startActivity(Intent(applicationContext,MyOrderActivity::class.java))
        }

        bottom_navigation.selectedItemId = R.id.profile

        // Perform item selected listener
        bottom_navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
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
                R.id.profile -> return@OnNavigationItemSelectedListener true
            }
            false
        })
    }
}