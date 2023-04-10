package com.example.restartonlyme

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.restartonlyme.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var pref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref = getSharedPreferences("Act", Context.MODE_PRIVATE)
        var indAct = pref.getInt("indAct", 0)
        var thread: Thread = object : Thread(){
            override fun run() {
                try {
                    TimeUnit.SECONDS.sleep(2)
                    if(indAct == 0){
                        var e = pref.edit()
                        e.putInt("indAct", 1)
                        e.apply()
                        startActivity(Intent(this@MainActivity, OnBoard::class.java))
                    }
                    if (indAct == 1)
                    {
                        startActivity(Intent(this@MainActivity, InputRegist::class.java))
                    }
                }
                catch (e: InterruptedException){
                    e.printStackTrace()
                }
            }
        }
        thread.start()
    }
}