package com.example.first_kotlin.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.first_kotlin.model.FormData



class DatabaseHelper(private val context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null, DATABASE_VERSION
) {

    companion object{
        private const val DATABASE_NAME = "userForm.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "users"

        // Column Names
        private const val COL_ID = "id"
        private const val COL_NAME = "name"
        private const val COL_EMAIL = "email"
        private const val COL_PASSWORD = "password"
        private const val COL_GENDER = "gender"
        private const val COL_CITY = "city"
        private const val COL_PAYMENT = "payment"
        private const val COL_BANK = "bank"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            create table $TABLE_NAME (
            $COL_ID integer primary key autoincrement,
            $COL_NAME text,
            $COL_EMAIL text,
            $COL_PASSWORD text,
            $COL_GENDER text,
            $COL_CITY text,
            $COL_PAYMENT text,
            $COL_BANK text
            )
        """.trimIndent()

        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        val dropTableQuery = "drop table if exists $TABLE_NAME"

        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertUserData(data: FormData){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, data.name)
        contentValues.put(COL_EMAIL, data.email)
        contentValues.put(COL_PASSWORD, data.password)
        contentValues.put(COL_GENDER, data.gender)
        contentValues.put(COL_CITY, data.city)
        contentValues.put(COL_PAYMENT, data.payment)
        contentValues.put(COL_BANK, data.bank)

        val result = db.insert(TABLE_NAME, null, contentValues)

        if (result == -1L) {
            // Syntax to add toast using the class context
            Toast.makeText(context, "Registration Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

}