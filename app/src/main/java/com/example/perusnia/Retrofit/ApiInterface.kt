package com.example.perusnia.Retrofit

import com.example.perusnia.Model.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiInterface {

    @FormUrlEncoded
    @POST("api/login.php?api_key=fasih123")
    fun userLogin(
        @Field("email")email:String,
        @Field("password")password:String
    ):Call<LoginResponse>
}