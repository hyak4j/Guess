package com.hyak4j.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.hyak4j.guess.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val secretNumber = SecretNumber()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("MainActivity", "secret number: ${secretNumber.secret}")

    }

    fun check(view : View){
        // btn_ok
        val num = binding.number.text.toString().toInt()
        println("number: $num")
        Log.d("MainActivity", "Input number: $num")
        val different = secretNumber.validate(num)
        var message = "You got it!"
        if (different < 0){
            message = "Bigger"
        }else if (different > 0){
            message = "Smaller"
        }
        AlertDialog.Builder(this)
            .setTitle("Message")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
        // Toast
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}