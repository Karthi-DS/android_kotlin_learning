package com.example.first_kotlin

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gender = resources.getStringArray(R.array.gender)
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,gender)
        val dropdown = findViewById<Spinner>(R.id.gender)
        dropdown.adapter = adapter;

        val btn = findViewById<Button>(R.id.btn);

        btn.setOnClickListener {
            val name = findViewById<EditText>(R.id.name).text.toString();
            val rollNo = findViewById<EditText>(R.id.rollNo).text.toString();
            val gender = findViewById<Spinner>(R.id.gender).selectedItem.toString();
            val rg = findViewById<RadioGroup>(R.id.radio);
            val checkedId = rg.checkedRadioButtonId;
            val dept = findViewById<RadioButton>(checkedId).text;
            Intent(this, MainActivity2::class.java).also {
                it.putExtra("name",name)
                it.putExtra("rollNo",rollNo)
                it.putExtra("gender",gender)
                it.putExtra("dept",dept)
                startActivity(it)
            }
        }
    }
}
