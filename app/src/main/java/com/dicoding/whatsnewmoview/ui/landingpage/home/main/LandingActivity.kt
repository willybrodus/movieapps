package com.dicoding.whatsnewmoview.ui.landingpage.home.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.dicoding.whatsnewmoview.databinding.ActivityLandingBinding
import com.dicoding.whatsnewmoview.ui.base.BaseActivity
import com.dicoding.whatsnewmoview.util.ext.observe
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LandingActivity : BaseActivity() {

    companion object {
            fun getIntent(context: Context) = Intent(context, LandingActivity::class.java)
        }

     lateinit var binding: ActivityLandingBinding
    private val viewModel: LandingViewModel by viewModels()
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeChange()
        setBottomNavigation()
    }

    override fun observeChange() {
        observe(viewModel.throwable, ::onErrorHandle)
    }

    private fun setBottomNavigation(){
        navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        NavigationUI.setupWithNavController(
            binding.bottomNavigation,
            navHostFragment.navController
        )
    }

}