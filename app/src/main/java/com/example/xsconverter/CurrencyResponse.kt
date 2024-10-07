package com.example.xsconverter

import com.google.gson.annotations.SerializedName

/**
 * Data class representing the response from the currency API.
 *
 * @param base The base currency code used in the exchange rate request (e.g., "EUR").
 * @param rates A map of currency codes to their corresponding exchange rates relative to the base currency.
 */
data class CurrencyResponse(
    @SerializedName("base_code") val base: String, // Maps the JSON field "base_code" to this variable
    @SerializedName("conversion_rates") val rates: Map<String, Double> // Maps the JSON field "conversion_rates" to this variable
)
