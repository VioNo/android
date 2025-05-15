package com.example.calculate2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var inputField: EditText
    private var currentInput = StringBuilder()
    private var firstOperand: Double = 0.0
    private var currentOperator: String? = null
    private var resetInput = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Инициализация элементов UI
        inputField = findViewById(R.id.inputField)

        // Настройка кнопок
        setupNumberButtons()
        setupOperationButtons()
        setupActionButtons()
    }

    private fun setupNumberButtons() {
        findViewById<Button>(R.id.button0).setOnClickListener { appendNumber("0") }
        findViewById<Button>(R.id.button1).setOnClickListener { appendNumber("1") }
        findViewById<Button>(R.id.button2).setOnClickListener { appendNumber("2") }
        findViewById<Button>(R.id.button3).setOnClickListener { appendNumber("3") }
        findViewById<Button>(R.id.button4).setOnClickListener { appendNumber("4") }
        findViewById<Button>(R.id.button5).setOnClickListener { appendNumber("5") }
        findViewById<Button>(R.id.button6).setOnClickListener { appendNumber("6") }
        findViewById<Button>(R.id.button7).setOnClickListener { appendNumber("7") }
        findViewById<Button>(R.id.button8).setOnClickListener { appendNumber("8") }
        findViewById<Button>(R.id.button9).setOnClickListener { appendNumber("9") }
        findViewById<Button>(R.id.buttonDecimal).setOnClickListener { appendNumber(".") }
    }

    private fun setupOperationButtons() {
        findViewById<Button>(R.id.buttonAdd).setOnClickListener { setOperator("+") }
        findViewById<Button>(R.id.buttonSubtract).setOnClickListener { setOperator("-") }
        findViewById<Button>(R.id.buttonMultiply).setOnClickListener { setOperator("*") }
        findViewById<Button>(R.id.buttonDivide).setOnClickListener { setOperator("/") }
    }

    private fun setupActionButtons() {
        findViewById<Button>(R.id.buttonClear).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.buttonEquals).setOnClickListener { calculateResult() }
    }

    private fun appendNumber(number: String) {
        if (resetInput) {
            currentInput.clear()
            resetInput = false
        }

        if (number == "." && currentInput.contains(".")) return
        if (number == "0" && currentInput.isEmpty()) return

        currentInput.append(number)
        inputField.setText(currentInput.toString())
    }

    private fun setOperator(operator: String) {
        if (currentInput.isNotEmpty()) {
            firstOperand = currentInput.toString().toDouble()
            currentOperator = operator
            resetInput = true
        } else if (currentOperator != null) {
            currentOperator = operator
        }
    }

    private fun calculateResult() {
        if (currentOperator == null || currentInput.isEmpty()) return

        val secondOperand = currentInput.toString().toDouble()
        val result = when (currentOperator) {
            "+" -> firstOperand + secondOperand
            "-" -> firstOperand - secondOperand
            "*" -> firstOperand * secondOperand
            "/" -> if (secondOperand == 0.0) "Error" else firstOperand / secondOperand
            else -> 0.0
        }

        inputField.setText(result.toString())
        currentInput.clear().append(result.toString())
        resetInput = true
        currentOperator = null
    }

    private fun clearAll() {
        currentInput.clear()
        inputField.setText("0")
        firstOperand = 0.0
        currentOperator = null
        resetInput = false
    }
}