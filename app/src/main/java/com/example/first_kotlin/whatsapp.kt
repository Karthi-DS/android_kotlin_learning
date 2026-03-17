package com.example.first_kotlin

import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.widget.AdapterView
import android.widget.SimpleCursorAdapter
import androidx.core.app.ActivityCompat

class whatsapp : AppCompatActivity() {
    private lateinit var list: ListView
    private lateinit var msg: String
    private lateinit var selectedNumber: String

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

        msg = intent.getStringExtra("msg").toString()

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

        list.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->

                val cursor = parent.getItemAtPosition(position) as Cursor

                selectedNumber = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    )
                )

                msgContact()
            }

    }
    private fun readContacts(){
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )

        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        if(cursor==null) return

        val from= arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        val to = intArrayOf(
            android.R.id.text1,
            android.R.id.text2
        )

        val adaptar = SimpleCursorAdapter(
            this,
            android.R.layout.simple_list_item_2,
            cursor,
            from,
            to,
            0
        )

        list.adapter = adaptar
    }

    private fun callContact(){
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE)!=
            android.content.pm.PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.CALL_PHONE),100)
        }else{
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$selectedNumber")
            startActivity(intent)
        }
    }

    private fun msgContact(){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://wa.me/$selectedNumber?text=${Uri.encode(msg)}")
        startActivity(intent)
    }
}