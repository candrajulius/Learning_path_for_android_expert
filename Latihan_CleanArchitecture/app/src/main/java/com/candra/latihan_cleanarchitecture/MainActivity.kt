package com.candra.latihan_cleanarchitecture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.candra.latihan_cleanarchitecture.databinding.ActivityMainBinding
import com.candra.latihan_cleanarchitecture.presentation.MainViewModel
import com.candra.latihan_cleanarchitecture.presentation.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = MainViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(this,factory)[MainViewModel::class.java]

        viewModel.setName("Dicoding")

        viewModel.message.observe(this, Observer {
            binding.tvWelcome.text = it.welcomeMessage
        })
    }
}