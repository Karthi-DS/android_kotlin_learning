package com.example.first_kotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.widget.Toast

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION){

            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)

            for(sms in messages){
                val sender = sms.displayOriginatingAddress
                val messageBody = sms.messageBody

                Toast.makeText(context,"From: $sender\nMessage: $messageBody",Toast.LENGTH_LONG).show()
            }
        }
    }
}