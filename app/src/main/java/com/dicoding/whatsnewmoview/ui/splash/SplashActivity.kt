package com.dicoding.whatsnewmoview.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.whatsnewmoview.databinding.ActivitySplashBinding
import com.dicoding.whatsnewmoview.ui.landingpage.home.main.LandingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.getMainLooper())
            .postDelayed({
                goToMainActivity()
            }, 2000)

    }

    private fun goToMainActivity() {
        startActivity(LandingActivity.getIntent(this))
        finish()
    }
}