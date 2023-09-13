package com.hyak4j.guess

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.hyak4j.guess.data.GameDatabase
import com.hyak4j.guess.data.Record
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

            // 儲存到Room
            val database = Room.databaseBuilder(this,
                GameDatabase::class.java, "game.db")
                .build()
            val record = Record(nick, count.toString().toInt())
            Thread(){
                //取得DB資料可能耗時
                database.recordDao().insert(record)
            }.start()

            val intent = Intent()
            intent.putExtra("NICK", nick)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}