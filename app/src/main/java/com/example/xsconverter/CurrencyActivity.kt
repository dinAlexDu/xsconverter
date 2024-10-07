package com.example.xsconverter

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xsconverter.databinding.ActivityCurrencyBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Activity that manages currency conversion, including fetching exchange rates from an API
 * and displaying them in a RecyclerView.
 */
class CurrencyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurrencyBinding
    private lateinit var adapter: CurrencyAdapter
    private val currencyList = mutableListOf<CurrencyItem>()  // Stores the currencies to display in the UI

    /**
     * Called when the activity is created.
     * Initializes the RecyclerView and starts fetching currency exchange rates.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrencyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initializes the RecyclerView with the adapter and layout manager
        adapter = CurrencyAdapter(currencyList)
        binding.currencyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.currencyRecyclerView.adapter = adapter

        // Calls the function to fetch exchange rates from the API (initial values only)
        getExchangeRates("EUR")
    }

    /**
     * Fetches exchange rates from the API.
     *
     * @param baseCurrency The base currency to fetch exchange rates for. Defaults to "EUR".
     */
    private fun getExchangeRates(baseCurrency: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://v6.exchangerate-api.com/v6/73a2f9b2a967e40908095e64/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CurrencyApiService::class.java)

        val apiCall = service.getExchangeRates(baseCurrency)

        // Performs the API call asynchronously
        apiCall.enqueue(object : Callback<CurrencyResponse> {
            override fun onResponse(call: Call<CurrencyResponse>, response: Response<CurrencyResponse>) {
                if (response.isSuccessful) {
                    val rates = response.body()?.rates
                    if (rates != null) {
                        // Updates the currency list with the fetched API rates and prepares the originalRates array
                        updateCurrencyListWithApiRates(rates)
                    }
                } else {
                    Log.e("CurrencyActivity", "Error in API response: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<CurrencyResponse>, t: Throwable) {
                Log.e("CurrencyActivity", "Error in API call: ${t.message}")
            }
        })
    }

    /**
     * Updates the list of currencies in the RecyclerView based on the exchange rates
     * obtained from the API. Also configures the originalRates array in the adapter.
     *
     * @param rates A map of currency codes to exchange rates.
     */
    private fun updateCurrencyListWithApiRates(rates: Map<String, Double>) {
        currencyList.clear()  // Clears the list before adding new values from the API

        // Adds EUR first
        rates["EUR"]?.let { rate ->
            val flagResId = getFlagForCurrency("EUR")
            if (flagResId != null) {
                currencyList.add(CurrencyItem("EUR", flagResId, rate))
            }
        }

        // Adds USD after EUR
        rates["USD"]?.let { rate ->
            val flagResId = getFlagForCurrency("USD")
            if (flagResId != null) {
                currencyList.add(CurrencyItem("USD", flagResId, rate))
            }
        }

        // Adds the remaining currencies, excluding EUR and USD, in the order provided by the API
        rates.forEach { (currencyCode, rate) ->
            if (currencyCode != "EUR" && currencyCode != "USD") {
                val flagResId = getFlagForCurrency(currencyCode)
                if (flagResId != null) {
                    currencyList.add(CurrencyItem(currencyCode, flagResId, rate))
                }
            }
        }

        adapter.updateOriginalRates(rates)  // Updates the original rates in the adapter
        adapter.notifyDataSetChanged()  // Notifies the adapter to refresh the RecyclerView
    }

    /**
     * Maps the currency codes to the appropriate flag icons in the drawable resources.
     *
     * @param currencyCode The currency code (e.g., "USD", "EUR").
     * @return The resource ID of the flag icon, or null if not found.
     */
    private fun getFlagForCurrency(currencyCode: String): Int? {
        return when (currencyCode) {
            "EUR" -> R.drawable.ic_euro
            "USD" -> R.drawable.ic_us_dollar
            "AUD" -> R.drawable.ic_australian_dollar
            "CAD" -> R.drawable.ic_canadian_dollar
            "CHF" -> R.drawable.ic_swiss_franc
            "CNY" -> R.drawable.ic_chinese_yuan
            "GBP" -> R.drawable.ic_british_pound
            "JPY" -> R.drawable.ic_japanese_yen
            "MXN" -> R.drawable.ic_mexican_peso
            "NZD" -> R.drawable.ic_new_zealand_dollar
            "PLN" -> R.drawable.ic_polish_zloty
            "TRY" -> R.drawable.ic_turkish_lira
            else -> null  // Returns null if the flag for the given currency is not found
        }
    }
}
