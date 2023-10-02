package com.hyak4j.guess

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class CacheService() : Service(){
    private val TAG = CacheService::class.java.simpleName

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ")
        return START_STICKY
        /*START_NOT_STICKY => 殺掉後不會再產生回來
        START_STICKY => 被殺掉後再自己產生回來*/
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
}