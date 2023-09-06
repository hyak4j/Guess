package com.hyak4j.guess

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.hyak4j.guess.databinding.ActivityMaterialBinding

class MaterialActivity : AppCompatActivity() {
    private lateinit var viewModel: GuessViewModel
    private lateinit var binding: ActivityMaterialBinding
    val TAG = MaterialActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         binding = ActivityMaterialBinding.inflate(layoutInflater)
         setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this).get(GuessViewModel::class.java)
        viewModel.counter.observe(this, Observer {data ->
            binding.includeid.counter.setText(data.toString())
        })

        viewModel.result.observe(this, Observer {result ->
            var message = when(result){
                GameResult.BIGGER -> "Bigger"
                GameResult.SMALLER -> "Smaller"
                GameResult.RIGHT -> "You are right"
            }
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.message))
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok), {dialog, which ->
                    if (result == GameResult.RIGHT){
                        // 猜對時導向，RecordActivity
                        val intent = Intent(this, RecordActivity::class.java)
                        intent.putExtra("COUNTER", binding.includeid.counter.text)
                        Log.d(TAG, "onCreate counter: ${binding.includeid.counter.text}")
                        startActivity(intent)
                    }
                })
                .show()
        })



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

    }

    private fun replay() {
        viewModel.reset()
        binding.includeid.number.setText("")
    }

    fun check(view : View){
        // btn_ok
        val n = binding.includeid.number.text.toString().toInt()
        viewModel.guess(n)
        /*val num = binding.includeid.number.text.toString().toInt()
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
            .show()*/
        // Toast
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}