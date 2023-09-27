package com.hyak4j.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.hyak4j.guess.EventResult
import com.hyak4j.guess.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.URL
import kotlin.coroutines.CoroutineContext

class SnookerActivity : AppCompatActivity(), CoroutineScope {
    companion object{
        val TAG = SnookerActivity::class.java.simpleName
    }
    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.IO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snooker)
        
        val viewModel = ViewModelProvider(this).get(SnookViewModel::class.java)
        viewModel.events.observe(this, Observer { events ->
            Log.d(TAG, "onCreate: ${events.size}")
        })
        /*launch {
            val data = URL("https://api.snooker.org/examples/5.json").readText()
            val events = Gson().fromJson(data, EventResult::class.java)
            events.forEach{
                Log.d(TAG, "onCreate: $it")
            }
        }*/
//        CoroutineScope(Dispatchers.IO).launch {
//            val data = URL("https://api.snooker.org/examples/5.json").readText()
//            val events = Gson().fromJson(data, EventResult::class.java)
//            events.forEach{
//                Log.d(TAG, "onCreate: $it")
//            }
//        }
    }



}