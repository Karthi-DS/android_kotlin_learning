package com.example.first_kotlin

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: EditText
    private var lastValue = 0.0
    private var currentOperator = ""
    private var isNewInput = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
            R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        )

        for (id in numberButtons) {
            findViewById<Button>(id).setOnClickListener {
                onNumberClick((it as Button).text.toString())
            }
        }

        findViewById<Button>(R.id.btnPlus).setOnClickListener { onOperatorClick("+") }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { onOperatorClick("-") }
        findViewById<Button>(R.id.btnMul).setOnClickListener { onOperatorClick("*") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { onOperatorClick("/") }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            display.setText("")
            lastValue = 0.0
            currentOperator = ""
            isNewInput = true
        }

        findViewById<Button>(R.id.btnEqual).setOnClickListener {
            calculateResult()
        }
    }

    private fun onNumberClick(value: String) {
        if (isNewInput) {
            display.setText(value)
            isNewInput = false
        } else {
            display.append(value)
        }
    }

    private fun onOperatorClick(operator: String) {
        if (display.text.isNotEmpty()) {
            lastValue = display.text.toString().toDouble()
            currentOperator = operator
            isNewInput = true
        }
    }

    private fun calculateResult() {
        if (display.text.isEmpty()) return

        val currentValue = display.text.toString().toDouble()

        if (currentOperator == "/" && currentValue == 0.0) {
            Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
            return
        }

        val result = when (currentOperator) {
            "+" -> lastValue + currentValue
            "-" -> lastValue - currentValue
            "*" -> lastValue * currentValue
            "/" -> lastValue / currentValue
            else -> currentValue
        }

        display.setText(result.toString())
        isNewInput = true
    }
}
