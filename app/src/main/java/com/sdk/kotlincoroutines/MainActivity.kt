package com.sdk.kotlincoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.sdk.kotlincoroutines.databinding.ActivityMainBinding
import com.sdk.kotlincoroutines.model.User
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val dataStoreManager = DataStoreManager(this)
        GlobalScope.launch(Dispatchers.IO) {
            dataStoreManager.getUser().collect {
                withContext(Dispatchers.Main) {
                    binding.textView.text = "${it?.name}${it?.age}"
                }
            }
        }
        binding.button.setOnClickListener {
            val name = binding.editTextTextPersonName.text.toString().trim()
            val age = binding.editTextTextPersonName2.text.toString().trim()

            if (name.isNotBlank() && age.isDigitsOnly()) {
                val user = User(name, age.toInt())
                GlobalScope.launch(Dispatchers.IO) {
                    dataStoreManager.saveUser(user)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity , "Data Saved", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Enter correct data!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}