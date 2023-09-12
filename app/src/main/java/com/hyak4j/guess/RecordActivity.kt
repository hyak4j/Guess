package com.hyak4j.guess

import android.content.Intent
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

        // 儲存按鈕 save
        binding.save.setOnClickListener { view ->
            val nick = binding.nickname.text.toString()
            getSharedPreferences("GUESS", MODE_PRIVATE)
                .edit()
                .putString("COUNTER", count)
                .putString("NICKNAME", nick)
                .apply()
            /*下一行就要讀取 => commit()  :馬上寫入
              之後才讀取    => apply()   :會在適當時間寫入*/
            val intent = Intent()
            intent.putExtra("NICK", nick)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}