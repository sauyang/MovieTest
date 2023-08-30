package com.example.myapplication.prelogin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.LoginSignupActivityBinding
import dagger.hilt.android.AndroidEntryPoint


class LoginSignupActivity : AppCompatActivity() {

    private lateinit var binding : LoginSignupActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginSignupActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))

        }
    }
}