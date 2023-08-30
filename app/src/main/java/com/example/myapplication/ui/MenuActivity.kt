package com.example.myapplication.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.MenuActivityBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MenuActivity : AppCompatActivity(){

    private lateinit var binding : MenuActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MenuActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}