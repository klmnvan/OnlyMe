package com.example.restartonlyme

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.restartonlyme.databinding.ActivityHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class Home : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    val adapterNews = AdapterNews()
    val adapterCategory = AdapterCategory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
    }

    fun initNews (news: List<NewsModel>){
        with(binding){
            listNews.layoutManager = GridLayoutManager(this@Home, news.size)
            listNews.adapter = adapterNews
            for(element in news){
                adapterNews.addNews(element)
            }
        }
    }

    fun initCategory(category: List<String>){
        with(binding){
            listCategory.layoutManager = GridLayoutManager(this@Home, category.size)
            listCategory.adapter
        }
    }

    fun getData(){
        var api = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://medic.madskill.ru")
            .build()
            .create(RequestApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.getNews().awaitResponse()
                val responseCat = api.getCatalog().awaitResponse()
                if(response.isSuccessful){
                    val data = response.body()!!
                    runOnUiThread{initNews(data)}
                }
                if(responseCat.isSuccessful){
                    val data = responseCat.body()!!
                    var listCategory = data.map { it.category }.toSet().toList()
                    runOnUiThread {  }
                }

            } catch (e:Exception){
                Log.d(ContentValues.TAG, e.toString())
            }
        }
    }
}