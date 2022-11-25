package com.example.perusnia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.perusnia.storage.SharedPrefManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val sharedPrefManager = SharedPrefManager.getInstance(this).user
        Log.d("sahredPrefManager",sharedPrefManager.email.toString())

        try {
            Picasso.get()
                .load("http://10.0.2.2/perusnia/api/files.php?api_key=fasih123&file=p1.png")
                .placeholder(R.drawable.ic_baseline_sync_24)
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(profile)
        }catch (e: Exception){
            Log.i("Picasso:","Message => "+e);
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
                R.id.profile -> return@OnNavigationItemSelectedListener true
            }
            false
        })
    }
}