package com.hyak4j.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hyak4j.guess.databinding.ActivityRecordBinding

class RecordActivity : AppCompatActivity() {
    val TAG = RecordActivity::class.java.simpleName
    private lateinit var binding: ActivityRecordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val count = intent.getStringExtra("COUNTER")
        binding.txtCount.setText(count.toString())
        Log.d(TAG, "onCreate count: $count")
    }
}