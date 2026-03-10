package com.example.first_kotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class SmsReceiver: BroadcastReceiver() {
    var smsData = sms("")
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION){
            val msgs = android.provider.Telephony.Sms.Intents.getMessagesFromIntent(intent)

            for(msg in msgs){
                val msgBody = msg.messageBody
                val intent = Intent("SMS_RECEIVED")
                intent.putExtra("msg_body",msgBody)
                context?.sendBroadcast(intent)
            }

        }
    }
}