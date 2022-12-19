package com.example.perusnia.Retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClient {
//    public const val BASE_URL = "http://10.0.2.2/perusnia/"
//      public const val BASE_URL = "http://192.168.43.74/perusnia/"
    public const val BASE_URL = "https://perusnia.ml/"
//    public const val BASE_URL = "https://perusnia.000webhostapp.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .method(original.method, original.body)

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: ApiInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(ApiInterface::class.java)
    }
}