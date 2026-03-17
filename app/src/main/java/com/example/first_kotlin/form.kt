package com.example.first_kotlin

import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val name = findViewById<EditText>(R.id.name)
        val email = findViewById<EditText>(R.id.email)
        val phone = findViewById<EditText>(R.id.phone)
        val age = findViewById<EditText>(R.id.age)
        val password = findViewById<EditText>(R.id.password)
        val confirmPassword = findViewById<EditText>(R.id.confirmPassword)
        val submit = findViewById<Button>(R.id.submitBtn)

        submit.setOnClickListener {

            val nameText = name.text.toString()
            val emailText = email.text.toString()
            val phoneText = phone.text.toString()
            val ageText = age.text.toString()
            val passText = password.text.toString()
            val confirmText = confirmPassword.text.toString()

            if(nameText.isEmpty()){
                name.error = "Name cannot be empty"
                name.requestFocus()
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
                email.error = "Invalid email address"
                email.requestFocus()
                return@setOnClickListener
            }

            if(phoneText.length != 10){
                phone.error = "Phone must be 10 digits"
                phone.requestFocus()
                return@setOnClickListener
            }

            if(ageText.isEmpty() || ageText.toInt() < 18){
                age.error = "Age must be 18 or above"
                age.requestFocus()
                return@setOnClickListener
            }

            if(passText.length < 6){
                password.error = "Password must be at least 6 characters"
                password.requestFocus()
                return@setOnClickListener
            }

            if(passText != confirmText){
                confirmPassword.error = "Passwords do not match"
                confirmPassword.requestFocus()
                return@setOnClickListener
            }

            Toast.makeText(this,"Registration Successful",Toast.LENGTH_LONG).show()
        }
    }
}