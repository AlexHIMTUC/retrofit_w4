package com.training.retrofit_w4.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.training.retrofit_w4.R
import com.training.retrofit_w4.helper.Const
import com.training.retrofit_w4.model.ProductionCompany


class ProductionCompanyAdapter(private val dataSet: List<ProductionCompany>) :
        RecyclerView.Adapter<ProductionCompanyAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var PCImage: ImageView
        var titleText :TextView

        init {
            // Define click listener for the ViewHolder's View.
            PCImage = view.findViewById(R.id.PCImage)
            titleText = view.findViewById(R.id.imgTitleText)

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.prodcompany, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if(dataSet.get(position).logo_path != null){
            Picasso.get().load(Const.BASE_IMG + dataSet.get(position).logo_path).into(viewHolder.PCImage)
            viewHolder.titleText.visibility = View.GONE
        }else{
            viewHolder.titleText.text = dataSet.get(position).name
            viewHolder.titleText.visibility = View.VISIBLE
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}

