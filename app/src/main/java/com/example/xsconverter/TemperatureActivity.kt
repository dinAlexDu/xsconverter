package com.example.xsconverter

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.xsconverter.databinding.ActivityTemperatureBinding

/**
 * TemperatureActivity handles temperature conversion between Celsius, Fahrenheit, and Kelvin.
 * The user can input a value and select the units for conversion, and the app will perform the calculation.
 */
class TemperatureActivity : AppCompatActivity() {

    // Binding object to access views from the activity's layout
    private lateinit var binding: ActivityTemperatureBinding

    /**
     * This method is called when the activity is created.
     * It sets up the user interface, including spinners for unit selection and input field for conversion.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTemperatureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Array of temperature units
        val units = arrayOf("Celsius", "Fahrenheit", "Kelvin")

        // Set up the adapter for the spinners with unit options
        val adapter = ArrayAdapter(this, R.layout.spinner_item, units)
        adapter.setDropDownViewResource(R.layout.spinner_item)
        binding.unitFromSpinner.adapter = adapter
        binding.unitToSpinner.adapter = adapter

        // Add a TextWatcher to monitor changes in the input field
        binding.inputValue.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val inputText = s.toString()
                val input = inputText.toDoubleOrNull()

                if (input != null) {
                    // If the input is valid, change the text color to black and perform conversion
                    binding.inputValue.setTextColor(Color.BLACK)
                    performConversion()
                } else {
                    // If the input is invalid, change the text color to red and clear the result
                    binding.inputValue.setTextColor(Color.RED)
                    binding.resultText.text = ""
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    /**
     * This function performs the temperature conversion based on the selected units.
     * It takes the input value and calculates the equivalent in the target unit.
     */
    private fun performConversion() {
        val inputText = binding.inputValue.text.toString().toDoubleOrNull()

        if (inputText != null) {
            val fromUnit = binding.unitFromSpinner.selectedItem.toString()
            val toUnit = binding.unitToSpinner.selectedItem.toString()

            // Perform conversion based on selected units
            val result = when (fromUnit to toUnit) {
                "Celsius" to "Fahrenheit" -> (inputText * 9 / 5) + 32
                "Fahrenheit" to "Celsius" -> (inputText - 32) * 5 / 9
                "Celsius" to "Kelvin" -> inputText + 273.15
                "Kelvin" to "Celsius" -> inputText - 273.15
                else -> inputText  // If no conversion needed (same unit)
            }

            // Display the conversion result and description
            binding.resultText.text = String.format("%.2f", result)
            val conversionDescription = String.format("%.2f %s = %.2f %s", inputText, fromUnit, result, toUnit)
            binding.conversionDescriptionText.text = conversionDescription
        } else {
            // Clear the result and description if the input is invalid
            binding.resultText.text = ""
            binding.conversionDescriptionText.text = ""
        }
    }
}
