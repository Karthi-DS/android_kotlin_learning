package com.example.first_kotlin

import android.Manifest
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.pm.PackageManager
import android.content.ContentResolver
import android.provider.ContactsContract
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Contacts : AppCompatActivity() {

    val cols = listOf<String>(
        ContactsContract.CommonDataKinds.Phone.SEARCH_DISPLAY_NAME_KEY,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contacts)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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

        }


    }
    private fun readContacts(){
        val c: ContentResolver = ContentResolver
        c.query()
    }
}