package mobiledev.unb.ca.temperatureconverter

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.RadioButton
import mobiledev.unb.ca.temperatureconverter.utils.ConverterUtils

class MainActivity : AppCompatActivity() {
    private var text: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text = findViewById(R.id.editText)
    }

    fun btnCalculateOnClickHandler(view: View) {
        if (view.id == R.id.btnCalculate) {
            val textStr = text!!.text.toString()
            if (textStr.isEmpty()) {
                Toast.makeText(this, "Please enter a valid number",
                    Toast.LENGTH_LONG).show()
            }
            val inputValue = getInputValue(textStr)
            val celsiusButton = findViewById<RadioButton>(R.id.rbCelcius)
            val fahrenheitButton = findViewById<RadioButton>(R.id.rbFahrenhiet)
            if (celsiusButton.isChecked) {
                text!!.setText(ConverterUtils.convertCelsiusToFahrenheit(inputValue).toString())
                celsiusButton.isChecked = false
                fahrenheitButton.isChecked = true
            } else {
                text!!.setText(ConverterUtils.convertFahrenheitToCelsius(inputValue).toString())
                fahrenheitButton.isChecked = false
                celsiusButton.isChecked = true
            }
        }
    }

    private fun getInputValue(textStr: String): Float {
        return textStr.toFloat()
    }
}