package com.example.restartonlyme

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.example.restartonlyme.databinding.ActivityInputCodeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class InputCode : AppCompatActivity() {
    lateinit var binding: ActivityInputCodeBinding
    lateinit var code: String
    lateinit var email: String
    var bool: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var arguments = intent.extras
        email = arguments?.getString("email").toString()
        timer()
        textChanged()
        var thread: Thread = object : Thread(){
            override fun run() {
                try {
                    TimeUnit.SECONDS.sleep(2)
                    startActivity(Intent(this@InputCode, Home::class.java))

                }
                catch (e: InterruptedException){
                    e.printStackTrace()
                }
            }
        }
    }

    fun timer(){
        var timer = object : CountDownTimer(60000,1000){
            override fun onTick(millisUntilFinished: Long) {
                val sec = millisUntilFinished/1000
                binding.textTimer.text = "Отправить код повторно можно будет через ${sec} секунд"
            }
            override fun onFinish() {
            }
        }
        timer.start()
    }

    /**
     * Метод, в котором будут отслеживаться заполненные поля и перескакивать фокус на след. цифры +
     * каждый раз открывается verification()
     */
    fun textChanged(){
        binding.num1.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(binding.num1.text.isNotEmpty()){
                    binding.num2.requestFocus()
                }
            }
        })
        binding.num2.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(binding.num2.text.isNotEmpty()){
                    binding.num3.requestFocus()
                }
            }
        })
        binding.num3.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(binding.num3.text.isNotEmpty()){
                    binding.num4.requestFocus()
                    binding.num4.text = null
                }
            }
        })
        binding.num4.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(binding.num4.text.isNotEmpty()){
                    verification()
                }
            }
        })
    }

    fun verification(){
        with(binding){
            if(num1.text.isNotEmpty() && num2.text.isNotEmpty() && num3.text.isNotEmpty() && num4.text.isNotEmpty()){
                code = num1.text.toString() + num2.text.toString() + num3.text.toString() + num4.text.toString()

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
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        requestApi.postCode(email,code).awaitResponse()
                        runOnUiThread { Toast.makeText(this@InputCode, "код неверный", Toast.LENGTH_LONG).show() }

                    } catch (e:Exception){
                        Log.d(ContentValues.TAG, e.toString())
                    }
                }
            }
        }
    }
}