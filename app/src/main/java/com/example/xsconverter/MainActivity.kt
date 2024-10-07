package com.example.xsconverter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xsconverter.databinding.ActivityMainBinding

/**
 * MainActivity handles the main menu, displaying the available conversion categories.
 * Each category directs the user to a different activity that handles a specific type of conversion.
 */
class MainActivity : AppCompatActivity() {

    // Binding object to access views from the activity's layout
    private lateinit var binding: ActivityMainBinding

    // List of categories with corresponding names and icons
    private val categories = listOf(
        Category("Temperature", "ic_temperature"),
        Category("Length", "ic_distance"),
        Category("Weight", "ic_weight"),
        Category("Currencies", "ic_currency")
    )

    /**
     * This method is called when the activity is created.
     * It sets up the user interface, including the RecyclerView that lists conversion categories.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the RecyclerView with a linear layout manager
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Create and set the adapter with the list of categories
        val adapter = CategoryAdapter(categories) { category ->
            // Start the appropriate activity based on the selected category
            val intent = when (category.name) {
                "Temperature" -> Intent(this, TemperatureActivity::class.java)
                "Length" -> Intent(this, LengthActivity::class.java)
                "Weight" -> Intent(this, WeightActivity::class.java)
                "Currencies" -> Intent(this, CurrencyActivity::class.java)
                else -> null
            }

            // If the intent is not null, start the corresponding activity
            intent?.let { startActivity(it) }
        }

        // Set the adapter to the RecyclerView
        binding.recyclerView.adapter = adapter
    }
}
