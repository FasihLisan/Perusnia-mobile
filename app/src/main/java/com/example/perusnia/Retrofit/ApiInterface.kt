package com.example.perusnia.Retrofit

import com.example.perusnia.Model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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

    @Multipart
    @POST("api/userUpdate.php?api_key=fasih123")
    fun userUpdateNoImage(
        @Query("id_users")id_users:Int,
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

    @GET("api/book.php?api_key=fasih123")
    fun getBook():Call<bookResponse>

    @GET("api/book.php?api_key=fasih123")
    fun getSpesificBook(@Query("id_book")id_book: Int):Call<bookResponse>

    @GET("api/searchBook.php?api_key=fasih123")
    fun serachBook(@Query("keyword")keyword:String):Call<bookResponse>

    @GET("api/checkMyBook.php?api_key=fasih123")
    fun check_mybook(
        @Query("id_users")id_users: Int,
        @Query("id_book")id_book: Int
    ):Call<DefaultResponse>

    @GET("api/getMyBookUsers.php?api_key=fasih123")
    fun getAllMyBookUsers(
        @Query("id_users")id_users: Int,
    ):Call<bookResponse>

    @GET("api/logUserRead.php?api_key=fasih123")
    fun getLogUserRead(
        @Query("id_users")id_users: Int,
    ):Call<singleBookResponse>

    @FormUrlEncoded
    @POST("api/insert_logUserRead.php?api_key=fasih123")
    fun insertOrUpdateLogUserRead(
        @Field("id_users")id_users: Int,
        @Field("id_book")id_book:Int,
    ):Call<DefaultResponse>







    /*
    **  ------Rate Book API------
    */
    @GET("api/getTopRateBook.php?api_key=fasih123")
    fun getTopRateBook():Call<TopRatedBook_Response>

    @GET("api/rateBook.php")
    fun getSpesificRateBook(
        @Query("api_key")api_key:String,
        @Query("id_users")id_users:Int,
        @Query("id_book")id_book:Int
    ):Call<SpesificRateBook_Response>

    @GET("api/rateBook.php?api_key=fasih123")
    fun getAllRateBook( @Query("id_book")id_book:Int):Call<BookRate_Response>

    @FormUrlEncoded
    @POST("api/insertFeedback.php?api_key=fasih123")
    fun insertFeedback(
        @Field("rate_score")rate_score:Float,
        @Field("comment")comment:String,
        @Field("id_book")id_book:Int,
        @Field("id_users")id_users: Int,
    ):Call<DefaultResponse>

    @FormUrlEncoded
    @POST("api/updateFeedback.php?api_key=fasih123")
    fun updateFeedback(
        @Query("id_book")id_book_params:Int,
        @Query("id_users")id_users_params: Int,
        @Field("rate_score")rate_score:Float,
        @Field("comment")comment:String,
    ):Call<DefaultResponse>

    @GET("api/deleteFeedback.php?api_key=fasih123")
    fun deleteFeedback(
        @Query("id_book")id_book_params:Int,
        @Query("id_users")id_users_params: Int,
    ):Call<DefaultResponse>



    /*
    **  ------Favorit API------
    */

    @GET("api/favoriteBook.php?api_key=fasih123")
    fun getFavorite(@Query("id_users")id_users: Int):Call<bookResponse>

    @GET("api/favoriteBook.php?api_key=fasih123")
    fun getSpesificFavorite(
        @Query("id_users")id_users: Int,
        @Query("id_book")id_book: Int
    ):Call<DefaultResponse>

    @FormUrlEncoded
    @POST("api/insertFavoriteBook.php?api_key=fasih123")
    fun insertFavorite(
        @Field("id_users") id_users: Int,
        @Field("id_book") id_book: Int
    ):Call<DefaultResponse>

    @FormUrlEncoded
    @POST("api/deleteFavoriteBook.php?api_key=fasih123")
    fun deleteFavorite(
        @Field("id_users") id_users: Int,
        @Field("id_book") id_book: Int
    ): Call<DefaultResponse>


    /*
    **  ------Note API------
    */

    @GET("api/notes.php?api_key=fasih123")
    fun getSpesificNote(@Query("id_users")id_users: Int):Call<noteResponse>

    @FormUrlEncoded
    @POST("api/insertNote.php?api_key=fasih123")
    fun insertNote(
        @Field("id_users") id_users: Int,
        @Field("judul") judul: String,
        @Field("isi") isi: String,
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("api/updateNote.php?api_key=fasih123")
    fun updateNote(
        @Query("id_notes") id_notes:Int,
        @Field("judul") judul: String,
        @Field("isi") isi: String,
    ): Call<DefaultResponse>

    @GET("api/deleteNote.php?api_key=fasih123")
    fun deleteNote(
        @Query("id_notes") id_notes:Int,
    ):Call<DefaultResponse>

    /*
    **  ------Cart API------
    */
    @GET("api/cart_item.php?api_key=fasih123")
    fun getSpesificCart( @Query("id_users")id_users: Int):Call<bookResponse>

    @FormUrlEncoded
    @POST("api/cart_item.php?api_key=fasih123")
    fun insertCart(
        @Field("id_users") id_users: Int,
        @Field("id_book") id_book: Int,
    ): Call<DefaultResponse>

    @GET("api/cart_item.php?api_key=fasih123")
    fun deleteCart(
        @Query("id_users") id_users: Int,
        @Query("id_book") id_book: Int
    ):Call<DefaultResponse>

    @GET("api/cartTotal.php?api_key=fasih123")
    fun getCartTotal(
        @Query("id_users") id_users: Int,
    ):Call<cartTotal_Response>

    @GET("api/deleteAllCartUsers.php?api_key=fasih123")
    fun deleteAllCartUsers(
        @Query("id_users") id_users: Int,
    ):Call<cartTotal_Response>


    //transaction

    @FormUrlEncoded
    @POST("api/insertDetaileTransaction.php?api_key=fasih123")
    fun insertDetaileTransaction(
        @Field("transaction_id") transaction_id: String,
        @Field("id_users") id_users: Int,
        @Field("id_book") id_book: Int,
        ): Call<DefaultResponse>

    @GET("api/getUserTransaction.php?api_key=fasih123")
    fun getUserTransaction(
        @Query("id_users")id_users: Int,
    ):Call<TransactionResponse>

    @GET("api/getDetileUserTransaction.php?api_key=fasih123")
    fun getDetileUserTransaction(
        @Query("transaction_id")transaction_id: String,
    ):Call<DetaileTransactionResponse>




}
