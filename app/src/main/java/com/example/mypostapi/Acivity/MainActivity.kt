package com.example.mypostapi.Acivity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mypostapi.Sharedpreferences.SharedpreferencesApi
import com.example.mypostapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedpre: SharedpreferencesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedpre = SharedpreferencesApi(this)
        binding.btngettingstart.setOnClickListener {
            startActivity(Intent(this@MainActivity, SighupActivity::class.java))
            finish()
             }
            binding.alreadyLogin.setOnClickListener {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
             finish()
            }
    }
}