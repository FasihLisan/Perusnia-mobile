package com.example.perusnia.Retrofit

import com.example.perusnia.Model.DefaultResponse
import com.example.perusnia.Model.LoginResponse
import com.example.perusnia.Model.userResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {

    /*
    **  ------Users API------
    */
    @GET("api/user.php?api_key=fasih123")
    fun getSpesificUser(@Query("id_users")id_users:Int):Call<userResponse>

    @FormUrlEncoded
    @POST("api/login.php?api_key=fasih123")
    fun userLogin(
        @Field("email")email:String,
        @Field("password")password:String
    ):Call<LoginResponse>

    @FormUrlEncoded
    @POST("api/userInsert.php?api_key=fasih123")
    fun userSignup(
        @Field("username")username:String,
        @Field("email")email:String,
        @Field("password")password:String,
        @Field("nama_depan")nama_depan:String,
        @Field("nama_belakang")nama_belakang:String,
        @Field("jenis_kelamin")jenis_kelamin:String,
        @Field("negara")negara:String,
        @Field("kota")kota:String,
    ): Call<DefaultResponse>

    @Multipart
    @POST("api/userUpdate.php?api_key=fasih123")
    fun userUpdate(
        @Query("id_users")id_users:Int,
        @Part foto:MultipartBody.Part,
        @Part("username")username:RequestBody,
        @Part("email")email:RequestBody,
        @Part("password")password:RequestBody,
        @Part("nama_depan")nama_depan:RequestBody,
        @Part("nama_belakang")nama_belakang:RequestBody,
        @Part("tgl_lahir")tgl_lahir:RequestBody,
        @Part("jenis_kelamin")jenis_kelamin:RequestBody,
        @Part("no_telp")no_telp:RequestBody,
        @Part("alamat")alamat:RequestBody,
        @Part("negara")negara:RequestBody,
        @Part("kota")kota:RequestBody,
    ): Call<DefaultResponse>


    /*
    **  ------Book API------
    */

    /*
    **  ------Note API------
    */

    /*
    **  ------Favorit API------
    */

    /*
    **  ------Other API------
    */
}
