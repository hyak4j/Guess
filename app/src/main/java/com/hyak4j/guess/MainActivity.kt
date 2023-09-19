package com.hyak4j.guess

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyak4j.guess.databinding.ActivityMainBinding

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
        "Maps"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            else -> return
        }
    }

    class FunctionHolder(view: View) : RecyclerView.ViewHolder(view){
        // 暫存
        var nameText: TextView = view.findViewById(R.id.name)
    }
}