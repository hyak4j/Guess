package com.hyak4j.guess

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.hyak4j.guess.databinding.ActivityMainBinding
import java.net.URL

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    val functions = listOf<String>(
        "Camera",
        "Guess Game",
        "Record List",
        "Invite friend",
        "Parking",
        "Download coupons",
        "News",
        "Snooker",
        "Maps"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Thread{
            val data = URL("https://api.snooker.org/examples/5.json").readText()
            val result = Gson().fromJson(data, EventResult::class.java)

            result.forEach {
                Log.d(TAG, "onCreate: $it")
            }
//            val jsonArray = JSONArray(data)
//            for (i in 0..jsonArray.length() - 1){
//                val jsonObject = jsonArray.getJSONObject(i)
//                val id = jsonObject.getInt("ID")
//                println(id)
//            }
        }.start()
        //RecyclerView
        var recycler = binding.recycler;
        recycler.layoutManager = LinearLayoutManager(this)
        // 固定大小
        recycler.setHasFixedSize(true)
        recycler.adapter = FunctionAdapter()
    }

    inner class FunctionAdapter() : RecyclerView.Adapter<FunctionHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_function, parent, false)
            val holder = FunctionHolder(view)
            return holder
        }

        override fun getItemCount(): Int {
            return functions.size
        }

        // 有資料時會呼叫
        override fun onBindViewHolder(holder: FunctionHolder, position: Int) {
            holder.nameText.text = functions.get(position)
            holder.itemView.setOnClickListener {
                functionClicked(position)
            }
        }

    }

    private fun functionClicked(position: Int) {
        when(position){
            1 -> startActivity(Intent(this, MaterialActivity::class.java))
            2 -> startActivity(Intent(this, RecordListActivity::class.java))
            7 -> startActivity(Intent(this, SnookerActivity::class.java))
            else -> return
        }
    }

    class FunctionHolder(view: View) : RecyclerView.ViewHolder(view){
        // 暫存
        var nameText: TextView = view.findViewById(R.id.name)
    }
}