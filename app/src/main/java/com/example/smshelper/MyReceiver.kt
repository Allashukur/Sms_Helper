package com.example.smshelper

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {

    @TargetApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {
//        Toast.makeText(context, "SMS : "+intent.getStringExtra("Sms_Masseg"), Toast.LENGTH_SHORT).show()

    }


}