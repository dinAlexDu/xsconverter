package com.example.xsconverter

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.xsconverter.databinding.ActivityLengthBinding

/**
 * This activity handles the length conversions between different units.
 */
class LengthActivity : AppCompatActivity() {

    // Binding object to access views from the activity's layout
    private lateinit var binding: ActivityLengthBinding

    /**
     * This method is called when the activity is created.
     * It sets up the user interface and handles user inputs for length conversion.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLengthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Array of length units for the dropdowns
        val units = arrayOf(
            "Meters",
            "Centimeters",
            "Inches",
            "Feet",
            "Mils",
            "Nautical Miles",
            "Yards",
            "Miles",
            "Millimeters",
            "Kilometers"
        )

        // Adapter for the spinners (dropdowns)
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
                    // If input is valid, change text color to black and perform conversion
                    binding.inputValue.setTextColor(Color.BLACK)
                    performConversion()
                } else {
                    // If input is invalid, change text color to red and clear result fields
                    binding.inputValue.setTextColor(Color.RED)
                    binding.resultText.text = ""
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    /**
     * Function to perform the length conversion based on the selected units.
     */
    private fun performConversion() {
        val inputText = binding.inputValue.text.toString().toDoubleOrNull()

        if (inputText != null) {
            val fromUnit = binding.unitFromSpinner.selectedItem.toString()
            val toUnit = binding.unitToSpinner.selectedItem.toString()

            // Perform conversion based on selected units
            val result = when (fromUnit to toUnit) {
                "Meters" to "Centimeters" -> inputText * 100
                "Meters" to "Inches" -> inputText * 39.3701
                "Meters" to "Feet" -> inputText * 3.28084
                "Meters" to "Mils" -> inputText * 39370.1
                "Meters" to "Nautical Miles" -> inputText * 0.000539957
                "Meters" to "Yards" -> inputText * 1.09361
                "Meters" to "Miles" -> inputText * 0.000621371
                "Meters" to "Millimeters" -> inputText * 1000
                "Meters" to "Kilometers" -> inputText / 1000

                // Add other conversions here

                else -> inputText
            }

            // Display the result
            binding.resultText.text = String.format("%.2f", result)

            // Display a description of the conversion
            val conversionDescription = String.format("%.2f %s = %.2f %s", inputText, fromUnit, result, toUnit)
            binding.conversionDescriptionText.text = conversionDescription
        } else {
            // Clear result fields if input is invalid
            binding.resultText.text = ""
            binding.conversionDescriptionText.text = ""
        }
    }
}
