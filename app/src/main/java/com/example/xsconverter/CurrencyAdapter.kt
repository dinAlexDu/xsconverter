package com.example.xsconverter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter for handling the list of currencies and their exchange rates. It includes logic for
 * updating conversion rates dynamically when the user inputs a value.
 *
 * @param currencyList List of CurrencyItem objects to display in the RecyclerView.
 */
class CurrencyAdapter(private val currencyList: MutableList<CurrencyItem>) :
    RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    // Stores the original exchange rates for accurate conversions
    private val originalRates: MutableList<Double> = currencyList.map { it.rate }.toMutableList()

    // Flag to prevent updating while handling user input
    private var isUpdating = false

    /**
     * Creates the ViewHolder for a currency item.
     *
     * @param parent The parent view group that will hold the view.
     * @param viewType The type of the view.
     * @return A new CurrencyViewHolder instance.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.currency_item, parent, false)
        return CurrencyViewHolder(view)
    }

    /**
     * Binds a currency item to the ViewHolder. Also sets up text listeners for dynamic currency conversion.
     *
     * @param holder The CurrencyViewHolder to bind the data to.
     * @param position The position of the currency item in the list.
     */
    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val actualPosition = holder.adapterPosition
        val currency = currencyList[actualPosition]
        holder.currencyCode.text = currency.code
        holder.currencyFlag.setImageResource(currency.flagResId)

        // Remove previous listener to avoid update loops
        holder.currencyInput.removeTextChangedListener(holder.textWatcher)

        // Set the currency value in the input field
        holder.currencyInput.setText(currency.rate.takeIf { it != 0.0 }?.toString() ?: "")

        // Sets up the TextWatcher to handle currency conversion on input
        holder.textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return

                val inputValue = s?.toString()?.toDoubleOrNull()
                if (inputValue != null && inputValue != 0.0) {
                    isUpdating = true

                    // Update the values of other currencies based on the input
                    for (i in currencyList.indices) {
                        if (i != actualPosition) {
                            val convertedValue = (inputValue / originalRates[actualPosition] * originalRates[i])
                            val formattedValue = String.format("%.4f", convertedValue).toDouble()

                            // Prevent updating if the value is NaN or Infinite
                            if (!formattedValue.isNaN() && !formattedValue.isInfinite()) {
                                currencyList[i] = currencyList[i].copy(rate = formattedValue)
                                holder.itemView.post {
                                    notifyItemChanged(i)
                                }
                            }
                        }
                    }
                    isUpdating = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        holder.currencyInput.addTextChangedListener(holder.textWatcher)
    }

    /**
     * Returns the total number of currency items in the list.
     *
     * @return The size of the currency list.
     */
    override fun getItemCount(): Int = currencyList.size

    /**
     * ViewHolder class for a single currency item. Holds references to the views for displaying
     * the currency code, flag, and input field for conversion.
     */
    class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val currencyCode: TextView = itemView.findViewById(R.id.currency_code)
        val currencyFlag: ImageView = itemView.findViewById(R.id.flag_image)
        val currencyInput: EditText = itemView.findViewById(R.id.currency_input)
        var textWatcher: TextWatcher? = null
    }

    /**
     * Updates the list of original exchange rates from the API. This function ensures
     * that conversions are accurate and reflect the latest rates.
     *
     * @param newRates A map of currency codes and their corresponding rates.
     */
    fun updateOriginalRates(newRates: Map<String, Double>) {
        if (currencyList.isEmpty()) return  // Check if the list is empty

        // Ensure `originalRates` matches the size of `currencyList`
        originalRates.clear()
        currencyList.forEach { item ->
            val rate = newRates[item.code] ?: item.rate
            originalRates.add(rate)
        }

        notifyDataSetChanged()
    }

}
