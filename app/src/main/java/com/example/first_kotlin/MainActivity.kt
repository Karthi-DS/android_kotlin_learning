package com.example.first_kotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.first_kotlin.data.User
import com.example.first_kotlin.database.DatabaseHelper

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var userAdapter: UserAdapter
    private var selectedUser: User? = null

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        btnAdd = findViewById(R.id.btnAdd)

        setupRecyclerView()

        btnAdd.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty()) {
                if (selectedUser == null) {
                    // Add new user
                    val id = dbHelper.addUser(name, email)
                    if (id != -1L) {
                        Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show()
                        clearFields()
                        refreshUserList()
                    }
                } else {
                    // Update existing user
                    dbHelper.updateUser(selectedUser!!.id, name, email)
                    Toast.makeText(this, "User Updated", Toast.LENGTH_SHORT).show()
                    clearFields()
                    selectedUser = null
                    btnAdd.text = "Add User"
                    refreshUserList()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        refreshUserList()
    }

    private fun setupRecyclerView() {
        val rvUsers = findViewById<RecyclerView>(R.id.rvUsers)
        userAdapter = UserAdapter(
            onEdit = { user ->
                selectedUser = user
                etName.setText(user.name)
                etEmail.setText(user.email)
                btnAdd.text = "Update User"
            },
            onDelete = { user ->
                dbHelper.deleteUser(user.id)
                Toast.makeText(this, "User Deleted", Toast.LENGTH_SHORT).show()
                refreshUserList()
            }
        )
        rvUsers.layoutManager = LinearLayoutManager(this)
        rvUsers.adapter = userAdapter
    }

    private fun refreshUserList() {
        val users = dbHelper.getAllUsers()
        userAdapter.submitList(users)
    }

    private fun clearFields() {
        etName.text.clear()
        etEmail.text.clear()
    }
}
