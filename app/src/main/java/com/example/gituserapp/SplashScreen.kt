package com.example.gituserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        GlobalScope.launch(Dispatchers.Main) {
            delayScreen()
        }
    }

    private suspend fun delayScreen(){
        delay(3000L)
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

}