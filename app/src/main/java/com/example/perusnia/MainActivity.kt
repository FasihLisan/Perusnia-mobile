package com.example.perusnia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.perusnia.Model.LoginResponse
import com.example.perusnia.Retrofit.RetrofitClient
import com.example.perusnia.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btn_signin
import kotlinx.android.synthetic.main.activity_main.txtEmail
import kotlinx.android.synthetic.main.activity_main.txtPassword
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val email = txtEmail.text.toString()
        val password = txtPassword.text.toString()



        btn_signin.setOnClickListener(){
            val email = txtEmail.text.toString().trim()
            val password = txtPassword.text.toString()

            if (email.isEmpty()){
                txtEmail.error = "Email required"
                txtEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()){
                txtPassword.error = "Email required"
                txtPassword.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.userLogin(email,password)
                .enqueue(object : Callback<LoginResponse?> {
                    override fun onResponse(
                        call: Call<LoginResponse?>,
                        response: Response<LoginResponse?>
                    ) {
                        if (!response.body()?.error!!){

                            SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.user!!)//save share prefences

                            val intent = Intent(applicationContext,HomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                            startActivity(intent)

                        }else{
                            Toast.makeText(applicationContext,response.body()?.message,Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                        Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show();
                    }
                })

        }






        btn_signup.setOnClickListener(){
            val intent = Intent(this, signupActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onStart() {
        super.onStart()

        if (SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext,HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
}
