package com.ozzikrangir.productlistnotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log



class Receiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val newIntent = Intent(context, BackgroundService::class.java)
        newIntent.putExtra("listId",intent.getIntExtra("listId",0))
        newIntent.putExtra("productId",intent.getIntExtra("productId",0))
        newIntent.putExtra("productName",intent.getStringExtra("productName"))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(newIntent);
        } else {
            context.startService(newIntent);
        }
    }


}