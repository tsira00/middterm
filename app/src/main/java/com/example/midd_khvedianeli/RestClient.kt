package com.example.midd_khvedianeli.api

import com.example.midd_khvedianeli.rec_service
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestClient {

    private lateinit var okHttpClient: OkHttpClient
    private lateinit var retrofit: Retrofit

    private val loggingInterceptor = HttpLoggingInterceptor()

    fun init() {
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)


        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()


        retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/Gogitajgarkava/Midd/refs/heads/main/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    fun getReqResService(): rec_service {
        return retrofit.create(rec_service::class.java)
    }
}
