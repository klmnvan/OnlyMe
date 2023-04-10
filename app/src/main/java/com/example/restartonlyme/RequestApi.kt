package com.example.restartonlyme

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST

interface RequestApi {
    @POST("/api/sendCode")
    fun sendCode(@Header("email") email: String): Call<String>
}