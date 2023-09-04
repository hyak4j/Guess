package com.hyak4j.guess

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import com.hyak4j.guess.databinding.ActivityMaterialBinding

class MaterialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMaterialBinding
    val secretNumber = SecretNumber()
    val TAG = MaterialActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityMaterialBinding.inflate(layoutInflater)
     setContentView(binding.root)

        setSupportActionBar(binding.toolbar)



        binding.fab.setOnClickListener { view ->
            AlertDialog.Builder(this)
                .setTitle("Replay Game")
                .setMessage("Are you sure?")
                .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    replay()
                }
                .setNeutralButton("Cancel", null)
                .show()
        }

        binding.includeid.counter.setText(secretNumber.count.toString())
    }

    private fun replay() {
        secretNumber.reset()
        binding.includeid.counter.setText(secretNumber.count.toString())
        binding.includeid.number.setText("")
    }

    fun check(view : View){
        // btn_ok
        val num = binding.includeid.number.text.toString().toInt()
        println("number: $num")
        Log.d(TAG, "Input number: $num")
        val different = secretNumber.validate(num)
        var message = getString(R.string.you_got_it)
        if (different < 0){
            message = getString(R.string.bigger)
        }else if (different > 0){
            message = getString(R.string.smaller)
        }

        binding.includeid.counter.setText(secretNumber.count.toString())

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.message))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), null)
            .show()
        // Toast
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}