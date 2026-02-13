package com.example.first_kotlin

import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    var cols = listOf<String>(
        ContactsContract.CommonDataKinds.Phone.SEARCH_DISPLAY_NAME_KEY,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID
    ).toTypedArray();

    private lateinit var l1: ListView
    private lateinit var c: Intent
    private lateinit var cur: Cursor
    private lateinit var i: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        l1 = findViewById<ListView>(R.id.l1)

        c = Intent(Intent.ACTION_CALL)

        if(ContextCompat.checkSelfPermission(
            this,
                Manifest.permission.READ_CONTACTS
        )!= PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(
                this,
                Array(1) { Manifest.permission.READ_CONTACTS},
                111
            )
        }else{
//            readContact()
        }
        l1.onItemClickListener = AdapterView.OnItemClickListener { parent,
            view, position, id ->
            cur = parent.adapter.getItem(position) as Cursor
            i = cur.getString(cur.getColumnIndexOrThrow("data1")) as String
//            start()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
    grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions,
            grantResults)
        if (requestCode == 111 && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED)
        readContact()
        if(requestCode==112 &&
        grantResults[0]==PackageManager.PERMISSION_GRANTED) {
            start()
        }
    }

    private fun readContact() {
        var from = listOf<String>(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID
        ).toTypedArray()
        var to = intArrayOf(android.R.id.text1, android.R.id.text2)
        var c: ContentResolver = contentResolver
        var s = c.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            cols,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )
        var a = SimpleCursorAdapter(this,
            android.R.layout.simple_list_item_2, s, from, to, 0)
        l1.adapter = a

    }

    private fun start() {
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                Array(1) { Manifest.permission.CALL_PHONE }, 112
            )
        } else {
            c.data = Uri.parse("tel"+i)
            startActivity(c)
        }
    }
}
