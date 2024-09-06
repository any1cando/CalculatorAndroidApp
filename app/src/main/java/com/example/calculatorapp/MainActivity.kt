package com.example.calculatorapp

import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculatorapp.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding  // ???
    private var inputText: String = ""
    private lateinit var operation: String
    private var savedNumber: Double? = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.e("OnCreate", "OnCreate is working")

        // Обрабатываем событие для очистки поля ввода
        binding.buttonAC.setOnClickListener {
            clearNumbersInput()
        }

        // Обрабатываем события для кнопок (цифр) 0-9
        binding.buttonNumber0.setOnClickListener {
            inputText += "0"
            if (checkStartZero(inputText)) { clearNumbersInput() } else { binding.numbersInput.text = inputText }
        }

        binding.buttonNumber1.setOnClickListener {
            inputText += "1"
            if (checkStartZero(inputText)) { clearNumbersInput() } else { binding.numbersInput.text = inputText }
        }

        binding.buttonNumber2.setOnClickListener {
            inputText += "2"
            if (checkStartZero(inputText)) { clearNumbersInput() } else { binding.numbersInput.text = inputText }
        }

        binding.buttonNumber3.setOnClickListener {
            inputText += "3"
            if (checkStartZero(inputText)) { clearNumbersInput() } else { binding.numbersInput.text = inputText }
        }

        binding.buttonNumber4.setOnClickListener {
            inputText += "4"
            if (checkStartZero(inputText)) { clearNumbersInput() } else { binding.numbersInput.text = inputText }
        }

        binding.buttonNumber5.setOnClickListener {
            inputText += "5"
            if (checkStartZero(inputText)) { clearNumbersInput() } else { binding.numbersInput.text = inputText }
        }

        binding.buttonNumber6.setOnClickListener {
            inputText += "6"
            if (checkStartZero(inputText)) { clearNumbersInput() } else { binding.numbersInput.text = inputText }
        }

        binding.buttonNumber7.setOnClickListener {
            inputText += "7"
            if (checkStartZero(inputText)) { clearNumbersInput() } else { binding.numbersInput.text = inputText }
        }

        binding.buttonNumber8.setOnClickListener {
            inputText += "8"
            if (checkStartZero(inputText)) { clearNumbersInput() } else { binding.numbersInput.text = inputText }
        }

        binding.buttonNumber9.setOnClickListener {
            inputText += "9"
            if (checkStartZero(inputText)) { clearNumbersInput() } else { binding.numbersInput.text = inputText }
        }

        binding.buttonCommaSign.setOnClickListener {
            val resultOfFuncComma = checkComma(inputText)
            inputText += resultOfFuncComma


        }

        // Обрабатываем событие для смены знака
        binding.buttonChangeSign.setOnClickListener {
            try {
                inputText = expressionChangeSign(inputText)
                binding.numbersInput.text = inputText
            } catch (e: Exception) {
                println(e.message)
                for (line in e.stackTrace) {
                    println(line)
                }
            }
        }

        binding.buttonPercentSign.setOnClickListener {
            inputText = expressionToPercents(inputText)
            binding.numbersInput.text = inputText
        }

        binding.buttonDivisionSign.setOnClickListener {
            operation = binding.buttonDivisionSign.text.toString()
            savedNumber = inputText.toDoubleOrNull()
            inputText = ""
        }
        binding.buttonMultiplySign.setOnClickListener {
            operation = binding.buttonMultiplySign.text.toString()
            savedNumber = inputText.toDoubleOrNull()
            inputText = ""
        }

        binding.buttonMinusSign.setOnClickListener {
            operation = binding.buttonMinusSign.text.toString()
            savedNumber = inputText.toDoubleOrNull()
            inputText = ""
        }

        binding.buttonPlusSign.setOnClickListener {
            Log.w("Pressed Sign Plus", "Нажал оператор +")
            operation = binding.buttonPlusSign.text.toString()
            savedNumber = inputText.toDoubleOrNull()
            inputText = ""
            Log.e("FirstNum", "savedNumber - $savedNumber")
            Log.e("SecondNum", "inputText - $inputText")

        }

        binding.buttonEqualsSign.setOnClickListener {
            val finalResult: Double? = when (operation) {
                "+" -> plusNumbers(savedNumber, inputText.toDoubleOrNull())
                "-" -> minusNumbers(savedNumber, inputText.toDoubleOrNull())
                "/" -> divisionNumbers(savedNumber, inputText.toDoubleOrNull())
                "*" -> multiplyNumbers(savedNumber, inputText.toDoubleOrNull())
                else -> 0.0
            }
            if (finalResult == 0.0) {
                Toast.makeText(this, "???", Toast.LENGTH_SHORT).show()
                savedNumber = 0.0
                inputText = ""
            } else {
                savedNumber = finalResult
                binding.numbersInput.text = finalResult.toString()
            }

        }
    }


    // Метод, который меняет знак у числа
    private fun expressionChangeSign(input: String): String {
        return if (input.startsWith('-')) {
            input.substring(1)
        } else {
            "-$input"
        }
    }


    // Метод, который делает из выражения процент
    private fun expressionToPercents(input: String): String {
        return try {
            val returnedNumber: Double? = input.toDoubleOrNull()
            if (returnedNumber != null) {
                (returnedNumber / 100).toString()
            } else {
                Toast.makeText(this, "Some error.", Toast.LENGTH_SHORT).show()
                "Error"
            }
        } catch (e: Exception) {
            showError(e)
            "Error"
        }
    }


    // Метод, который показывает ошибку, а также выводит ее в консоль
    private fun showError(error: Exception) {
        println(error.message)
        for (line in error.stackTrace) {
            println(line)
        }
    }


    // Метод, который проверяет ноль в начале, если мы вводим число
    private fun checkStartZero(input: String): Boolean {
        return when {
            input.length == 1 && input[0] == '0' -> true
//            input.length == 2 && input[0] == '0' && input[1] == '0' -> true
            else -> false
        }
    }


    private fun clearNumbersInput() {
        binding.numbersInput.text = "0.0"
        inputText = ""
    }


    private fun checkComma(input: String): String {
        return if (input.isEmpty()) {
            binding.numbersInput.text = "0."
            "0."
        } else if (input.contains('.')) {
            Toast.makeText(this, "Can't use 2 commas at one time!", Toast.LENGTH_LONG).show()
            ""
        } else {
            Toast.makeText(this, "Comma's set", Toast.LENGTH_SHORT).show()
            binding.numbersInput.text = input.plus('.')
            "."
        }
    }

    private fun plusNumbers(firstNumber: Double?, secondNumber: Double?): Double? {
        Log.w("Plus Functions Started", "Запустилась функция сложения с числами $firstNumber и $secondNumber")
        return if (firstNumber != null && secondNumber != null) {
            Log.e("Success", "Вернул ${firstNumber + secondNumber}")
            firstNumber + secondNumber
        } else {
            Log.e("Fuck up", "Вернул 0")
            0.0
        }
    }


    private fun minusNumbers(firstNumber: Double?, secondNumber: Double?): Double? {
        return if (firstNumber != null && secondNumber != null) { firstNumber - secondNumber } else { 0.0 }
    }


    private fun multiplyNumbers(firstNumber: Double?, secondNumber: Double?): Double? {
        return if (firstNumber != null && secondNumber != null) { firstNumber * secondNumber } else { 0.0 }
    }

    private fun divisionNumbers(firstNumber: Double?, secondNumber: Double?): Double? {
        return if (firstNumber != null && secondNumber != null) {

            try { firstNumber / secondNumber }catch (e: Exception) {
                showError(e)
                0.0
            }
        }
        else { 0.0 }
    }

    // В разработке
//    private fun calculate(operation(): (Number, Number) -> Number, number1: Number, number2:Number): String {
//
//    }
}