package com.hyak4j.guess

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyak4j.guess.data.GameDatabase
import com.hyak4j.guess.databinding.ActivityRecordListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecordListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecordListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //執行耗時工作
        CoroutineScope(Dispatchers.IO).launch {
            // 取得紀錄
            val records = GameDatabase.getInstance(this@RecordListActivity)?.
            recordDao()?.getAll()

            records?.let {
                // records 不是null時才會到這往下執行
                withContext(Dispatchers.Main){
                    // 指示這段是在Main Thread執行
                    // 建構RecyclerView
                    var recyclerView = binding.recycler
                    recyclerView.layoutManager = LinearLayoutManager(this@RecordListActivity)
                    recyclerView.setHasFixedSize(true)
                    recyclerView.adapter = RecordAdapter(it)
                }
            }
        }
    }
}