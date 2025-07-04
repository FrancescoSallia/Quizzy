package com.example.quizzy.ui.SplashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import com.example.quizzy.MainActivity
import com.example.quizzy.R
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Optional: Splash verzögern (z.B. 2 Sekunden)
        Handler(Looper.getMainLooper()).postDelayed({
            // Weiterleitung zur MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }
}