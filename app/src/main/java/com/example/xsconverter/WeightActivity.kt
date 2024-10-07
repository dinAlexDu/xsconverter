package com.example.xsconverter

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.xsconverter.databinding.ActivityWeightBinding

/**
 * WeightActivity handles weight conversion between Kilograms, Pounds, Grams, and Ounces.
 * The user inputs a value and selects the units for conversion, and the app performs the calculation.
 */
class WeightActivity : AppCompatActivity() {

    // Binding object to access views from the activity's layout
    private lateinit var binding: ActivityWeightBinding

    /**
     * This method is called when the activity is created.
     * It sets up the user interface, including spinners for unit selection and the input field for conversion.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeightBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // List of weight units for the spinner
        val units = arrayOf("Kilograms", "Pounds", "Grams", "Ounces")

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
     * This function performs the weight conversion based on the selected units.
     * It takes the input value and calculates the equivalent in the target unit.
     */
    private fun performConversion() {
        val inputText = binding.inputValue.text.toString().toDoubleOrNull()

        if (inputText != null) {
            val fromUnit = binding.unitFromSpinner.selectedItem.toString()
            val toUnit = binding.unitToSpinner.selectedItem.toString()

            // Perform conversion based on selected units
            val result = when (fromUnit to toUnit) {
                "Kilograms" to "Pounds" -> inputText * 2.20462
                "Pounds" to "Kilograms" -> inputText / 2.20462
                "Grams" to "Ounces" -> inputText * 0.035274
                "Ounces" to "Grams" -> inputText / 0.035274
                else -> inputText  // If no conversion needed (same unit)
            }

            // Display the conversion result
            binding.resultText.text = String.format("%.2f", result)

            // Display the conversion description
            val conversionDescription = String.format("%.2f %s = %.2f %s", inputText, fromUnit, result, toUnit)
            binding.conversionDescriptionText.text = conversionDescription
        } else {
            // Clear the result and description if the input is invalid
            binding.resultText.text = ""
            binding.conversionDescriptionText.text = ""
        }
    }
}
