package com.hyak4j.guess

import android.content.Context
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
    private val REQUEST_RECORD = 100
    private lateinit var viewModel: GuessViewModel
    private lateinit var binding: ActivityMaterialBinding
    val TAG = MaterialActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")

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
                .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    if (result == GameResult.RIGHT) {
                        // 猜對時導向，RecordActivity
                        val intent = Intent(this, RecordActivity::class.java)
                        intent.putExtra("COUNTER", binding.includeid.counter.text)
                        Log.d(TAG, "onCreate counter: ${binding.includeid.counter.text}")
                        startActivityForResult(intent, REQUEST_RECORD)
                    }
                }
                .show()
        })


        // 右下角fab按鈕 => 執行重玩
        binding.fab.setOnClickListener { view ->
            replay_Dialog()
        }
        val count = getSharedPreferences("GUESS", Context.MODE_PRIVATE)
            .getString("COUNTER", "-1")
        val nick = getSharedPreferences("GUESS", Context.MODE_PRIVATE)
            .getString("NICKNAME", null)
        Log.d(TAG, "onCreate share: $count / $nick")
    }

    private fun replay_Dialog() {
        AlertDialog.Builder(this)
            .setTitle("Replay Game")
            .setMessage("Are you sure?")
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                replay()
            }
            .setNeutralButton("Cancel", null)
            .show()
    }

    private fun replay() {
        viewModel.reset()
        binding.includeid.number.setText("")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    fun test(){
//        val intent = Intent(this, RecordActivity::class.java)
//        intent.putExtra("A", "AA")
//        intent.putExtra("B", "BB")
//        startActivity(intent)
        // Kotlin intent apply also測試
        Intent(this, RecordActivity::class.java).apply {
            putExtra("A", "AA")
            putExtra("B", "BB")
        }.also {intent->
            startActivity(intent)
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_RECORD){
            // RecordActivity返回
            if (resultCode == RESULT_OK){
                val nickname = data?.getStringExtra("NICK")
                Log.d(TAG, "onActivityResult: nickname $nickname")
                replay_Dialog()
            }
        }
    }
}