package com.example.midd_khvedianeli

import Artist
import ReqresObj
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface rec_service {
    @GET("mid")
    fun getCities(@Query("page") page: Int): Call<Artist<ReqresObj<Artist<Any?>>>>


}