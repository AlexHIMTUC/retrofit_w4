package com.training.retrofit_w4.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.training.retrofit_w4.R
import com.training.retrofit_w4.helper.Const
import com.training.retrofit_w4.model.Result
import com.training.retrofit_w4.view.MovieDetailActivity


class RetrofitPageAdapter(private val dataSet: ArrayList<Result>) :
        RecyclerView.Adapter<RetrofitPageAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var subtitle: TextView
        var card : CardView
        var img : ImageView
        init {
            // Define click listener for the ViewHolder's View.
            title = view.findViewById(R.id.title)
            subtitle = view.findViewById(R.id.subtitle)
            card = view.findViewById(R.id.curCard)
            img = view.findViewById(R.id.movieImg)

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.cur_card_view, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.title.text = dataSet[position].title
        viewHolder.subtitle.text = dataSet[position].release_date
        viewHolder.card.setOnClickListener{
            val intent = Intent(it.context, MovieDetailActivity::class.java)
            intent.putExtra("movie_id", dataSet[position].id)
            it.context.startActivity(intent)
        }
        Picasso.get().load(Const.BASE_IMG + dataSet.get(position).backdrop_path).into(viewHolder.img);

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}

