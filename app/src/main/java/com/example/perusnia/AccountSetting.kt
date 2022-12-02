package com.example.perusnia

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.perusnia.Model.DefaultResponse
import com.example.perusnia.Model.userResponse
import com.example.perusnia.Model.userUploadRequestBody
import com.example.perusnia.Retrofit.RetrofitClient
import com.example.perusnia.storage.SharedPrefManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_account_setting.*
import kotlinx.android.synthetic.main.activity_account_setting.btn_back
import kotlinx.android.synthetic.main.activity_account_setting.rGenderGroup
import kotlinx.android.synthetic.main.activity_account_setting.txtCity
import kotlinx.android.synthetic.main.activity_account_setting.txtCountry
import kotlinx.android.synthetic.main.activity_account_setting.txtEmail
import kotlinx.android.synthetic.main.activity_account_setting.txtFirstname
import kotlinx.android.synthetic.main.activity_account_setting.txtLastname
import kotlinx.android.synthetic.main.activity_account_setting.txtPassword
import kotlinx.android.synthetic.main.activity_account_setting.txtPasswordVerification
import kotlinx.android.synthetic.main.activity_account_setting.txtUsername
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_signup.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date


class AccountSetting : AppCompatActivity(), userUploadRequestBody.UploadCallback {

    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_setting)
        val sharedPrefManager = SharedPrefManager.getInstance(this).user
        val id_users = sharedPrefManager.id_users.toInt()
        val radio:RadioButton = findViewById(rGenderGroup.checkedRadioButtonId)



        RetrofitClient.instance.getSpesificUser(id_users)
            .enqueue(object : Callback<userResponse?> {
                override fun onResponse(
                    call: Call<userResponse?>,
                    response: Response<userResponse?>
                ) {
                    txtUsername.setText(response.body()?.data!!.username)
                    txtEmail.setText(response.body()?.data!!.email)
                    txtFirstname.setText(response.body()?.data!!.namaDepan)
                    txtLastname.setText(response.body()?.data!!.namaBelakang)
                    txtTglLahir.setText(response.body()?.data!!.tglLahir)
                    if (response.body()?.data!!.jenisKelamin.equals("laki-laki")){
                        rGenderGroup.check(R.id.rMale)
                    }else{
                        rGenderGroup.check(R.id.rFemale)
                    }
                    txtTelp.setText(response.body()?.data!!.noTelp)
                    txtAlamat.setText(response.body()?.data!!.alamat)
                    txtCountry.setText(response.body()?.data!!.negara)
                    txtCity.setText(response.body()?.data!!.kota)
                }

                override fun onFailure(call: Call<userResponse?>, t: Throwable) {
                    Toast.makeText(this@AccountSetting,t.message,Toast.LENGTH_LONG).show()
                }
            })





        //back
        btn_back.setOnClickListener(){
           startActivity(Intent(applicationContext,ProfileActivity::class.java))
        }

        //api user
        RetrofitClient.instance.getSpesificUser(id_users)
            .enqueue(object : Callback<userResponse?> {
                override fun onResponse(
                    call: Call<userResponse?>,
                    response: Response<userResponse?>
                ) {

                    try {
                        Picasso.get()
                            .load("http://10.0.2.2/perusnia/api/files.php?api_key=fasih123&file=${response.body()?.data!!.foto}")
                            .placeholder(R.drawable.default_image)
                            .error(R.drawable.ic_baseline_error_outline_24)
                            .into(profileImage)
                    }catch (e: Exception){
                        Log.i("Picasso:","Message => "+e)
                    }


                }

                override fun onFailure(call: Call<userResponse?>, t: Throwable) {
                    Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
                }
            })

        //date picker
        val date =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        txtTglLahir.setOnClickListener {
            date.show(supportFragmentManager,"tag")
        }
        date.addOnPositiveButtonClickListener {
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
            val date = dateFormatter.format(Date(it))
            txtTglLahir.setText(date)
        }


        //avatar upload
        btn_EditAvatar.setOnClickListener {
            openImageChooser()
        }



        btn_save.setOnClickListener {
            var username = txtUsername.text.toString()
            var email = txtEmail.text.toString()
            var password = txtPassword.text.toString()
            var passwordVerif = txtPasswordVerification.text.toString()
            var nama_Depan = txtFirstname.text.toString()
            var nama_belakang = txtLastname.text.toString()
            var tgl_lahir = txtTglLahir.text.toString()
            var jenis_kelamin = radio.text.toString()
            var no_telp = txtTelp.text.toString()
            var alamat = txtAlamat.text.toString()
            var negara = txtCountry.text.toString()
            var kota = txtCity.text.toString()
            if (username.isEmpty()){
                txtUsername.error = "Username required"
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
            uploadImage(id_users,username,email,passwordVerif,nama_Depan,nama_belakang,tgl_lahir,jenis_kelamin,no_telp,alamat,negara,kota)
        }
        btn_save2.setOnClickListener {
            var username = txtUsername.text.toString()
            var email = txtEmail.text.toString()
            var password = txtPassword.text.toString()
            var passwordVerif = txtPasswordVerification.text.toString()
            var nama_Depan = txtFirstname.text.toString()
            var nama_belakang = txtLastname.text.toString()
            var tgl_lahir = txtTglLahir.text.toString()
            var jenis_kelamin = radio.text.toString()
            var no_telp = txtTelp.text.toString()
            var alamat = txtAlamat.text.toString()
            var negara = txtCountry.text.toString()
            var kota = txtCity.text.toString()
            if (username.isEmpty()){
                txtUsername.error = "Username required"
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
            uploadImage(id_users,username,email,passwordVerif,nama_Depan,nama_belakang,tgl_lahir,jenis_kelamin,no_telp,alamat,negara,kota)
        }

    }

    private fun uploadImage(id_users:Int, username:String, email:String, passwordVerif:String, nama_Depan:String, nama_belakang:String, tgl_lahir:String, jenis_kelamin:String, no_telp:String, alamat:String, negara:String, kota:String){
        if (selectedImageUri == null){
            layout_root.snackbar("Select an image first")
            return
        }

        val parcelFileDescriptor =
            contentResolver.openFileDescriptor(selectedImageUri!!, "r", null) ?: return


        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(cacheDir, contentResolver.getFileName(selectedImageUri!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        progress_bar.progress = 0
        val body = userUploadRequestBody(file, "image", this)

        RetrofitClient.instance.userUpdate(
            id_users,
            MultipartBody.Part.createFormData("foto", file.name, body),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),username),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),email),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),passwordVerif),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),nama_Depan),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),nama_belakang),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),tgl_lahir),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),jenis_kelamin),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),no_telp),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),alamat),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),negara),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),kota),
            ).enqueue(object : Callback<DefaultResponse?> {
            override fun onResponse(
                call: Call<DefaultResponse?>,
                response: Response<DefaultResponse?>
            ) {
                response.body()?.let {
                    layout_root.snackbar(it.message)
                    progress_bar.progress = 100
//               startActivity(Intent(applicationContext,AccountSetting::class.java))
                }
            }

            override fun onFailure(call: Call<DefaultResponse?>, t: Throwable) {
                layout_root.snackbar(t.message!!)
                progress_bar.progress = 0
            }
        })

    }

    private fun openImageChooser(){
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png","image/jpg")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    selectedImageUri = data?.data
                    profileImage.setImageURI(selectedImageUri)
                }
            }
        }
    }

    override fun onProgressUpdate(percentage: Int) {
        progress_bar.progress = percentage
    }


    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 101
    }

}