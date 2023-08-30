package com.example.myapplication.prelogin


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.myapplication.R
import com.example.myapplication.databinding.LoginActivityBinding
import com.example.myapplication.ui.MenuActivity
import dagger.hilt.android.AndroidEntryPoint


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginActivityBinding
    private var username: String? = null
    private var password: String? = null

    fun validateUsernamePassword(username: String?, password: String?): Boolean {

        if (username != null && password != null ) {
            if (username.equals("VVVBB,", ignoreCase = false) && password.equals("@bcd1234", ignoreCase = false)
            ) {
                //match
                binding.tvUserPasswordEmpty.isVisible = false
                return true;
            } else if (username.isEmpty() || password.isEmpty()) {
                binding.tvUserPasswordEmpty.setText(resources.getString(R.string.usernamePasswordEmpty))
                binding.tvUserPasswordEmpty.isVisible = true
                return false
            } else {
                binding.tvUserPasswordEmpty.setText(resources.getString(R.string.wrongUsernamePasswrd))
                binding.tvUserPasswordEmpty.isVisible = true
                return false
            }
        }
        binding.tvUserPasswordEmpty.isVisible = true
        binding.tvUserPasswordEmpty.setText(resources.getString(R.string.usernamePasswordEmpty))
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.customActionBar.llBackButton.setOnClickListener {
            finish()
        }

        binding.etUsername.addTextChangedListener (object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    binding.tvUserPasswordEmpty.isVisible = false
                    username = s.toString()
                }
            })

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                binding.tvUserPasswordEmpty.isVisible = false
                password = s.toString()
            }
        })

        binding.loginBtn.setOnClickListener{
            val isEmailPasswordValid = validateUsernamePassword(username = username, password = password)
            if(isEmailPasswordValid)
                startActivity(Intent(this, MenuActivity::class.java))
        }

        binding.etUsername.setText("VVVBB,")

    }
}