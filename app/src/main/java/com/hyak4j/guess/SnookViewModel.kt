package com.hyak4j.guess

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class SnookViewModel: ViewModel() {
    val events = MutableLiveData<List<Event>>()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            val data = URL("https://api.snooker.org/examples/5.json").readText()
            events.postValue(Gson().fromJson(data, EventResult::class.java))
//            events.value = Gson().fromJson(data, EventResult::class.java)
        }
    }
}