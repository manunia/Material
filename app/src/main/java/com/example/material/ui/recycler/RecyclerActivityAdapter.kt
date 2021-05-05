package com.example.material.ui.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.material.R
import com.example.material.ui.picture.responceData.PODServerResponseData
import kotlinx.android.synthetic.main.activity_recycler_item.view.*

class RecyclerActivityAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: List<PODServerResponseData?>
) : RecyclerView.Adapter<RecyclerView.ViewHolder> () {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EarthViewHolder(
            inflater.inflate(R.layout.activity_recycler_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as EarthViewHolder
        data[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return data.size
    }

//    override fun getItemViewType(position: Int): Int {
//        return if (data[position]?.explanation.isNullOrBlank()) TYPE_MARS else TYPE_EARTH
//    }

    inner class EarthViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: PODServerResponseData) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.descriptionTextView.text = data.explanation
                itemView.wikiImageView.setOnClickListener { onListItemClickListener.onItemClick(data) }
            }
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(data: PODServerResponseData)
    }

//    companion object {
//        private const val TYPE_EARTH = 0
//        private const val TYPE_MARS = 1
//    }
}