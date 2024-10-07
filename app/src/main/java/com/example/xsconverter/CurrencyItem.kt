package com.example.xsconverter

/**
 * Data class representing a currency item in the app.
 *
 * @param code The currency code (e.g., "USD", "EUR") that uniquely identifies the currency.
 * @param flagResId The resource ID for the flag image associated with the currency.
 * @param rate The current exchange rate for the currency relative to a base currency.
 */
data class CurrencyItem(
    val code: String,
    val flagResId: Int,
    val rate: Double
)
