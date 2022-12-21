package com.example.perusnia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.perusnia.Model.DefaultResponse
import com.example.perusnia.Retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.txtEmail
import kotlinx.android.synthetic.main.activity_signup.txtPassword
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class signupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        btn_back.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btn_Signup.setOnClickListener {
            val radio:RadioButton = findViewById(rGenderGroup.checkedRadioButtonId)

            val username = txtUsername.text.toString()
            val email = txtEmail.text.toString()
            val password = txtPassword.text.toString()
            val passwordVerif = txtPasswordVerification.text.toString()
            val nama_Depan = txtFirstname.text.toString()
            val nama_belakang = txtLastname.text.toString()
            val jenis_kelamin = radio.text.toString()
            val negara = txtCountry.text.toString()
            val kota = txtCity.text.toString()
            val regex = Regex("^[a-zA-Z0-9](_(?!(\\.|_))|\\.(?!(_|\\.))|[a-zA-Z0-9]){6,18}[a-zA-Z0-9]\$")

            if (username.isEmpty()){
                txtUsername.error = "Username required"
                txtUsername.requestFocus()
                return@setOnClickListener
            }
            if (username.length < 5){
                txtUsername.error = "Minimal 5 karakter"
                txtUsername.requestFocus()
                return@setOnClickListener
            }
            if (!regex.containsMatchIn(username)){
                txtUsername.error = "min 6,max 18 alpahnumeric"
                txtUsername.requestFocus()
                return@setOnClickListener
            }
            if (nama_Depan.isEmpty()){
                txtFirstname.error = "Firstname required"
                txtFirstname.requestFocus()
                return@setOnClickListener
            }
            if (nama_belakang.isEmpty()){
                txtLastname.error = "Lastname required"
                txtLastname.requestFocus()
                return@setOnClickListener
            }
            if (negara.isEmpty()){
                txtCountry.error = "Country required"
                txtCountry.requestFocus()
                return@setOnClickListener
            }
            if (kota.isEmpty()){
                txtCity.error = "City required"
                txtCity.requestFocus()
                return@setOnClickListener
            }
            if (email.isEmpty()){
                txtEmail.error = "Email required"
                txtEmail.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()){
                txtPassword.error = "Password required"
                txtPassword.requestFocus()
                return@setOnClickListener
            }
            if (passwordVerif.isEmpty()){
                txtPasswordVerification.error = "Password Verif required"
                txtPasswordVerification.requestFocus()
                return@setOnClickListener
            }

            //cek pasword dan password ferive sama atau tidak
            if (!passwordVerif.equals(password)){
                txtPasswordVerification.error = "Password Verification not match"
                txtPasswordVerification.requestFocus()
                return@setOnClickListener
            }


            RetrofitClient.instance.userSignup(username,email,passwordVerif,nama_Depan,nama_belakang,jenis_kelamin,negara,kota)
                .enqueue(object : Callback<DefaultResponse?> {
                    override fun onResponse(
                        call: Call<DefaultResponse?>,
                        response: Response<DefaultResponse?>
                    ) {
                        if (response.isSuccessful){
                            if (!response.body()?.status!!.equals(400)){
                                Toast.makeText(applicationContext,response.body()?.message,Toast.LENGTH_LONG).show()
                                val intent = Intent(applicationContext,MainActivity::class.java)
                                startActivity(intent)
                            }else{
                                Toast.makeText(applicationContext,response.body()?.message,Toast.LENGTH_LONG).show()
                            }
                        }else{
                            Toast.makeText(applicationContext,response.body()?.message,Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<DefaultResponse?>, t: Throwable) {
                        Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show();
                    }
                })


        }

    }
}