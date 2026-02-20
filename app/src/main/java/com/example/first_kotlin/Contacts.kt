package com.example.first_kotlin

import android.Manifest
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.pm.PackageManager
import android.content.Intent
import android.provider.ContactsContract
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.database.Cursor
import android.net.Uri
import android.telephony.SmsManager

class Contacts : AppCompatActivity() {

    private lateinit var listView: ListView
    var selectedNumber: String = ""

    var msg: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contacts)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        msg = intent.getStringExtra("msg").toString()
        listView = findViewById<ListView>(R.id.list)
        if(
            ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
            )!= PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(
                this,
                Array(1){ Manifest.permission.READ_CONTACTS},
                111)
        }else{
            readContacts()
        }

        listView.setOnItemClickListener {
                parent,
                _,
                position ,
                _->

            val cursor: Cursor = parent.getItemAtPosition(position) as Cursor

            val columnIndex: Int = cursor.getColumnIndexOrThrow(
                ContactsContract.CommonDataKinds.Phone.NUMBER
            )

            selectedNumber = cursor.getString(columnIndex)

            sendMessage()
        }

    }

    private fun readContacts(){
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )

        val from = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        val to = intArrayOf(
            android.R.id.text1,
            android.R.id.text2
        )

        val adapter = SimpleCursorAdapter(
            this,
            android.R.layout.simple_list_item_2,
            cursor,
            from,
            to,
            0
        )
        listView.adapter = adapter
    }

    private fun callContact(){
        if(
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            )!= PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                112
                )
        }else{
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$selectedNumber")
            startActivity(intent)
        }
    }

    private fun sendMessage(){
        if(
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            )!= PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.SEND_SMS),
                101
            )
        }else{
            sendSms()
        }
    }

    private fun sendSms(){
        println(selectedNumber + " " + msg)
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(
            selectedNumber,
            null,
            msg,
            null,
            null
        )
    }

}