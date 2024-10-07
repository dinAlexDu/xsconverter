package com.example.xsconverter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xsconverter.Category

/**
 * Adapter class for managing and displaying categories in a RecyclerView.
 * @param categories A list of Category objects to be displayed.
 * @param onClick A lambda function that defines the behavior when a category is clicked.
 */
class CategoryAdapter(
    private val categories: List<Category>,
    private val onClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category, onClick)
    }

    /**
     * Returns the total number of items in the dataset held by the adapter.
     */
    override fun getItemCount(): Int = categories.size

    /**
     * ViewHolder class that represents each category item in the RecyclerView.
     */
    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryText: TextView = itemView.findViewById(R.id.category_name)
        private val iconImageView: ImageView = itemView.findViewById(R.id.icon)

        /**
         * Binds the data from the Category object to the view elements.
         * @param category The Category object representing the current item.
         * @param onClick Lambda function that defines behavior on item click.
         */
        fun bind(category: Category, onClick: (Category) -> Unit) {
            categoryText.text = category.name
            val context = itemView.context
            // Uses the icon name from the category to set the appropriate image
            val resourceId = context.resources.getIdentifier(category.iconName, "drawable", context.packageName)
            iconImageView.setImageResource(resourceId)
            itemView.setOnClickListener { onClick(category) }
        }
    }
}
