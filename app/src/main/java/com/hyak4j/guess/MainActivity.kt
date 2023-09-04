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
    val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "secret number: ${secretNumber.secret}")

    }

    fun check(view : View){
        // btn_ok
        val num = binding.number.text.toString().toInt()
        println("number: $num")
        Log.d(TAG, "Input number: $num")
        val different = secretNumber.validate(num)
        var message = getString(R.string.you_got_it)
        if (different < 0){
            message = getString(R.string.bigger)
        }else if (different > 0){
            message = getString(R.string.smaller)
        }
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.message))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), null)
            .show()
        // Toast
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}