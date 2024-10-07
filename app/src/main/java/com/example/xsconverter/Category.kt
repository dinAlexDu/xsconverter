package com.example.xsconverter

/**
 * Data class representing a category in the converter application.
 *
 * @property name The name of the category (e.g., Temperature, Length, Weight, etc.).
 * @property iconName The name of the icon associated with the category, typically the name of the drawable resource.
 */
data class Category(
    val name: String,      // Name of the category (e.g., "Temperature")
    val iconName: String   // Icon name for the category's drawable resource
)
