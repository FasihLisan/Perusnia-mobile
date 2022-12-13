package com.example.perusnia


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.perusnia.Model.DataXXXX
import com.example.perusnia.Model.DefaultResponse
import com.example.perusnia.Retrofit.RetrofitClient
import com.example.perusnia.storage.SharedPrefManager
import com.htmleditor.HtmlTextEditor
import kotlinx.android.synthetic.main.activity_note_detile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NoteDetileActivity : AppCompatActivity() {

    private lateinit var htmlTextEditor: HtmlTextEditor

    val sharedPrefManager = SharedPrefManager.getInstance(this).user
    val id_users = sharedPrefManager.id_users.toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detile)

        val note = intent.getParcelableExtra<DataXXXX>("data")

        btn_back.setOnClickListener(){
            startActivity(Intent(applicationContext,NoteActivity::class.java))
        }

        if (note != null){
            htmlTextEditor = html_editor
            judul.setText(note.judul)
            htmlTextEditor.text = note.isi
            btn_save.setOnClickListener(){
                update(note.idNotes!!.toInt())
            }
        }else{
            btn_save.setOnClickListener(){
                save()
            }
        }


    }
    private fun update(Id_note:Int){
        var id_note = Id_note
        var title = judul.text.toString()
        val isi = htmlTextEditor.text
        RetrofitClient.instance.updateNote(id_note,title,isi)
            .enqueue(object : Callback<DefaultResponse?> {
                override fun onResponse(
                    call: Call<DefaultResponse?>,
                    response: Response<DefaultResponse?>,
                ) {
                    Toast.makeText(applicationContext,response.body()?.message, Toast.LENGTH_LONG).show()
                    startActivity(Intent(applicationContext,NoteActivity::class.java))
                }

                override fun onFailure(call: Call<DefaultResponse?>, t: Throwable) {
                    Toast.makeText(applicationContext,t.message.toString(),Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun save(){
            htmlTextEditor = html_editor
            var title = judul.text.toString()
            val isi = htmlTextEditor.text
            var id = id_users

            RetrofitClient.instance.insertNote(id,title,isi.toString())
                .enqueue(object : Callback<DefaultResponse?> {
                    override fun onResponse(
                        call: Call<DefaultResponse?>,
                        response: Response<DefaultResponse?>,
                    ) {
                        Toast.makeText(applicationContext,response.body()?.message, Toast.LENGTH_LONG).show()
                        startActivity(Intent(applicationContext,NoteActivity::class.java))
                    }

                    override fun onFailure(call: Call<DefaultResponse?>, t: Throwable) {
                        Toast.makeText(applicationContext,t.message.toString(),Toast.LENGTH_LONG).show()
                    }
                })

    }

}