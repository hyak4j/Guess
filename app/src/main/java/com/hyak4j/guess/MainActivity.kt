package com.hyak4j.guess

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.hyak4j.guess.databinding.ActivityMainBinding
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val REQUEST_CAMERA = 200
    val TAG = MainActivity::class.java.simpleName
    var cacheService : Intent? = null
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
        var recycler = binding.recycler
        recycler.layoutManager = LinearLayoutManager(this)
        // 固定大小
        recycler.setHasFixedSize(true)
        recycler.adapter = FunctionAdapter()

        //Spinner
        var spinner = binding.spinner
        val colors = arrayOf("Red", "Green", "Blue")
        val apapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colors)
        apapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner.adapter = apapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.d(TAG, "onItemSelected: ${colors[position]}")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
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
            0 -> {
                // 拍照
                val permission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                if (permission == PackageManager.PERMISSION_GRANTED){
                    takePhoto()
                }else {
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA)
                }

            }
            1 -> startActivity(Intent(this, MaterialActivity::class.java))
            2 -> startActivity(Intent(this, RecordListActivity::class.java))
            7 -> startActivity(Intent(this, SnookerActivity::class.java))
            else -> return
        }
    }

    private fun takePhoto() {
        // 進行拍照
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 取得相機權限請求結果
        if (requestCode == REQUEST_CAMERA){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                takePhoto()
            }
        }
    }

    class FunctionHolder(view: View) : RecyclerView.ViewHolder(view){
        // 暫存
        var nameText: TextView = view.findViewById(R.id.name)
    }

    // 取得menu物件
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // 選擇menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_cache){
            Log.d(TAG, "onOptionsItemSelected: Cache selected")
            cacheService = Intent(this, CacheService::class.java)
            startService(cacheService)
            startService(Intent(this, CacheService::class.java))
            startService(Intent(this, CacheService::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        stopService(cacheService)
    }
}