package com.hyak4j.guess

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Random

// 呼叫父類別建構子 ViewModel()
class GuessViewModel : ViewModel(){
    val TAG = GuessViewModel::class.java.simpleName
    var secret: Int = 0
    var count: Int = 0
    // 觀察Actitivy counter值
    val counter = MutableLiveData<Int>()
    val result = MutableLiveData<GameResult>()
    init {
        counter.value = count
        reset()
    }

    fun guess(num: Int){
        count++
        counter.value = count
        val gameResult = when(num - secret){
            0 -> GameResult.RIGHT
            in 1..Int.MAX_VALUE -> GameResult.SMALLER
            else -> GameResult.BIGGER
        }
        result.value = gameResult
    }

    fun reset(){
        counter.value = 0
        secret = Random().nextInt(10) + 1
        Log.d(TAG, "reset secret: $secret")
        count = 0
    }
}

// 定義遊戲結果
enum class GameResult{
    BIGGER, SMALLER, RIGHT
}