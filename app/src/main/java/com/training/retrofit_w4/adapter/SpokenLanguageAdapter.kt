package com.training.retrofit_w4.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.training.retrofit_w4.R
import com.training.retrofit_w4.model.SpokenLanguage


class SpokenLanguageAdapter(private val dataSet: List<SpokenLanguage>, private val color : Int) :
        RecyclerView.Adapter<SpokenLanguageAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var langText : TextView
        var langBtn : LinearLayout
        init {
            // Define click listener for the ViewHolder's View.
            langText = view.findViewById(R.id.languageText)
            langBtn = view.findViewById(R.id.langBtn)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.spoken_language, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.langText.text = dataSet.get(position).name
        viewHolder.langBtn.setBackgroundColor(color)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}

