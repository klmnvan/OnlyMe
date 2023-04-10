package com.example.restartonlyme

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.restartonlyme.databinding.ActivityInputRegistBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class InputRegist : AppCompatActivity() {
    lateinit var binding: ActivityInputRegistBinding
    lateinit var email: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputRegistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        with(binding){
            buttonDalee.setOnClickListener(){
                if(inputEmailtext.text.length > 3){
                    email = inputEmailtext.text.toString()
                    var interceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                    var httpClient = OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .build()
                    var api = Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://medic.madskill.ru")
                        .client(httpClient)
                        .build()
                    var requestApi = api.create(RequestApi::class.java)
                    CoroutineScope(Dispatchers.IO).launch{
                        try {
                            requestApi.sendCode(email).awaitResponse()
                            Log.d("Response", "success")

                        }catch (e: Exception){
                            Log.d(ContentValues.TAG, e.toString())
                        }
                    }
                    var intent = Intent(this@InputRegist, InputCode::class.java)
                    intent.putExtra("email", email)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}