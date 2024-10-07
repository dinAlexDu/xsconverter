package com.example.xsconverter

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interface for the Currency API service using Retrofit.
 * This service is used to fetch the latest exchange rates from a given base currency.
 */
interface CurrencyApiService {

    /**
     * Fetches the latest exchange rates for the specified base currency.
     *
     * @param baseCurrency The base currency code (e.g., "USD", "EUR") to retrieve exchange rates for.
     * @return A Retrofit Call object containing the CurrencyResponse with exchange rates.
     */
    @GET("latest/{base}")
    fun getExchangeRates(@Path("base") baseCurrency: String): Call<CurrencyResponse>
}
