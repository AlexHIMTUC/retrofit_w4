package com.training.retrofit_w4.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.training.retrofit_w4.R
import com.training.retrofit_w4.model.Genre


class GenreListAdapter(private val dataSet: List<Genre>, private val color : Int) :
    RecyclerView.Adapter<GenreListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var genreText: TextView
        var genreBtn : LinearLayout
        init {
            // Define click listener for the ViewHolder's View.
            genreText = view.findViewById(R.id.genreText)
            genreBtn = view.findViewById(R.id.genreBtn)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.genre_card_view, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.genreText.text = dataSet.get(position).name
        viewHolder.genreBtn.setBackgroundColor(color)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}

