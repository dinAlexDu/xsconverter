package com.example.xsconverter

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.xsconverter.databinding.ActivityConversionBinding

/**
 * Activity responsible for handling conversions between different units.
 * In this specific implementation, the units being converted are temperature units.
 */
class ConversionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConversionBinding

    /**
     * Called when the activity is first created.
     * Sets up the layout, unit spinners, and text input listeners.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConversionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Array of available units for conversion
        val units = arrayOf("Celsius", "Fahrenheit", "Kelvin")

        // Sets up the spinners with the units array using ArrayAdapter
        val adapter = ArrayAdapter(this, R.layout.spinner_item, units)
        adapter.setDropDownViewResource(R.layout.spinner_item)
        binding.unitFromSpinner.adapter = adapter
        binding.unitToSpinner.adapter = adapter

        // Adds a TextWatcher to the input field to handle changes in the input value
        binding.inputValue.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val inputText = s.toString()
                val input = inputText.toDoubleOrNull()

                // If input is valid, perform conversion; otherwise, show an error
                if (input != null) {
                    binding.inputValue.setTextColor(Color.BLACK)  // Valid input
                    performConversion()
                } else {
                    binding.inputValue.setTextColor(Color.RED)  // Invalid input
                    binding.resultText.text = ""
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    /**
     * Performs the conversion based on selected units from the spinner and the input value.
     * Updates the UI with the conversion result and a description of the conversion.
     */
    private fun performConversion() {
        val inputText = binding.inputValue.text.toString().toDoubleOrNull()

        if (inputText != null) {
            // Retrieves the selected units from the spinners
            val fromUnit = binding.unitFromSpinner.selectedItem.toString()
            val toUnit = binding.unitToSpinner.selectedItem.toString()

            // Performs the conversion based on the units selected
            val result = when (fromUnit to toUnit) {
                "Celsius" to "Fahrenheit" -> (inputText * 9 / 5) + 32
                "Fahrenheit" to "Celsius" -> (inputText - 32) * 5 / 9
                "Celsius" to "Kelvin" -> inputText + 273.15
                "Kelvin" to "Celsius" -> inputText - 273.15
                else -> inputText  // Default case where no conversion is needed
            }

            // Formats and displays the result
            binding.resultText.text = String.format("%.2f", result)

            // Adds a descriptive string of the conversion to display
            val conversionDescription = String.format("%.2f %s = %.2f %s", inputText, fromUnit, result, toUnit)
            binding.conversionDescriptionText.text = conversionDescription
        } else {
            // Clears the result fields if the input is invalid
            binding.resultText.text = ""
            binding.conversionDescriptionText.text = ""
        }
    }
}
