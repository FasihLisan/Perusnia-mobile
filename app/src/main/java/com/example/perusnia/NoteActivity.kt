package com.example.perusnia

import android.content.DialogInterface.OnShowListener
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perusnia.Model.*
import com.example.perusnia.Retrofit.RetrofitClient
import com.example.perusnia.adapter.NoteAdapter
import com.example.perusnia.storage.SharedPrefManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_note.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NoteActivity : AppCompatActivity() {

    lateinit var noteAdapter: NoteAdapter

    val sharedPrefManager = SharedPrefManager.getInstance(this).user
    val id_users = sharedPrefManager.id_users.toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        bottom_navigation.selectedItemId = R.id.note

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
                R.id.note -> return@OnNavigationItemSelectedListener true
                R.id.profile -> {
                    startActivity(Intent(applicationContext, ProfileActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })

        btn_addNote.setOnClickListener(){
            startActivity(Intent(applicationContext,NoteDetileActivity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
        setupRecylerView()
        getDataFromAPI()
    }

    private fun setupRecylerView(){
        noteAdapter = NoteAdapter(arrayListOf(),object : NoteAdapter.OnAdapterListener {
            override fun onClick(data: DataXXXX) {
                intent = Intent(applicationContext,NoteDetileActivity::class.java)
                intent.putExtra("data",data)
                startActivity(intent)
            }

            override fun onLongClick(data: DataXXXX) {
                val builder = AlertDialog.Builder(this@NoteActivity)
                builder.setMessage("Are you sure you want to Delete?")
                    .setCancelable(false)
                    .setPositiveButton("Delete") { dialog, id ->
                        RetrofitClient.instance.deleteNote(data.idNotes!!.toInt())
                            .enqueue(object : Callback<DefaultResponse?> {
                                override fun onResponse(
                                    call: Call<DefaultResponse?>,
                                    response: Response<DefaultResponse?>,
                                ) {
                                    recreate()
                                    Toast.makeText(applicationContext,response.body()!!.message,Toast.LENGTH_LONG).show()
                                }

                                override fun onFailure(call: Call<DefaultResponse?>, t: Throwable) {
                                    Toast.makeText(applicationContext,t.message.toString(),Toast.LENGTH_LONG).show()
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
                alert.getWindow()?.setGravity(Gravity.BOTTOM);
            }
        })
        recyclerview.apply {
            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL,false)
            adapter = noteAdapter
        }
    }

    private fun getDataFromAPI(){

        RetrofitClient.instance.getSpesificNote(id_users)
            .enqueue(object : Callback<noteResponse?> {
                override fun onResponse(
                    call: Call<noteResponse?>,
                    response: Response<noteResponse?>,
                ) {
                    if (response.body()?.status == 200){
                        showData(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<noteResponse?>, t: Throwable) {
                    recyclerview.visibility = View.GONE
                    Toast.makeText(applicationContext,"data not found", Toast.LENGTH_LONG).show()
                }
            })
    }
    private fun showData(response: noteResponse){
        val datas = response.data!!
        noteAdapter.setData(datas)
    }
}