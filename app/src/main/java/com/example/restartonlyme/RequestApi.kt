package com.example.restartonlyme

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RequestApi {
    @POST("/api/sendCode")
    fun sendCode(@Header("email") email: String): Call<String>

    @POST("/api/signin")
    fun postCode(@Header("email") email: String, @Header("code") code: String): Call<String>

    @GET("api/news")
    fun getNews(): Call<List<NewsModel>>

    @GET("api/catalog")
    fun getCatalog(): Call<List<CatalogModel>>


}