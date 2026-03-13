package com.example.first_kotlin

import android.content.ContentResolver
import android.database.Cursor
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.pm.PackageManager
import android.provider.ContactsContract
import androidx.core.app.ActivityCompat
import java.util.jar.Manifest

class whatsapp : AppCompatActivity() {
    private lateinit var list: ListView
    private lateinit var cursor: Cursor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_whatsapp)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        list = findViewById<ListView>(R.id.list)

        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_CONTACTS)!=
            PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_CONTACTS),
                111
            )
        }else{
            readContacts()
        }
    }
    private fun readContacts(){
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.SEARCH_DISPLAY_NAME_KEY
        )

        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.SEARCH_DISPLAY_NAME_KEY + "ASC"
        )

        if(cursor==null) return

        val from = arrayOf(
            ContactsContract.CommonDataKinds.Phone.SEARCH_DISPLAY_NAME_KEY,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        val to = arrayOf(
            androidx.core.R.id.text1,
            an
        )
    }
}