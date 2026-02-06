package com.example.first_kotlin

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.media.MediaPlayer
import android.provider.Settings


class MyService : Service() {

    private lateinit var player: MediaPlayer

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);

        player.start();

        return START_STICKY //return flag

    }

    override fun onDestroy() {
        player.stop();
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}