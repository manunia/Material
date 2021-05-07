package com.example.material.ui.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.material.R
import kotlinx.android.synthetic.main.activity_recycler_item_earth.view.*
import kotlinx.android.synthetic.main.activity_recycler_item_mars.view.*


class RecyclerActivityAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: MutableList<Data>
) :
    RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_EARTH -> EarthViewHolder(
                inflater.inflate(R.layout.activity_recycler_item_earth, parent, false) as View
            )
            TYPE_MARS -> MarsViewHolder(
                inflater.inflate(R.layout.activity_recycler_item_mars, parent, false) as View
            )
            else -> HeaderViewHolder(
                inflater.inflate(R.layout.activity_recycler_item_header, parent, false) as View
            )
        }


    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> TYPE_HEADER
            data[position].someDescription.isNullOrBlank() -> TYPE_MARS
            else -> TYPE_EARTH
        }
    }

    inner class EarthViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Data) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.descriptionTextView.text = data.someDescription
                itemView.earthImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(
                        data
                    )
                }
            }
        }
    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Data) {
            itemView.marsImageView.setOnClickListener { onListItemClickListener.onItemClick(data) }

            itemView.mars_add_button.setOnClickListener {
                addItem()
            }

            itemView.mars_remove_button.setOnClickListener {
                removeItem()
            }

            itemView.moveItemDown.setOnClickListener { moveDown() }
            itemView.moveItemUp.setOnClickListener { moveUp() }

        }

        private fun addItem() {
            data.add(layoutPosition, generateImportantNote())
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }

        }

        private fun moveDown() {
            layoutPosition.takeIf { it > data.size - 1 }?.also { currentPosition ->

            }
        }

    }


    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Data) {
            itemView.setOnClickListener { onListItemClickListener.onItemClick(data) }
        }
    }

    private fun generateImportantNote() = Data("MArs", "")

    fun appendItem() {
        data.add(generateImportantNote())
        notifyDataSetChanged()
    }

    interface OnListItemClickListener {
        fun onItemClick(data: Data)
    }

    companion object {
        private const val TYPE_EARTH = 0
        private const val TYPE_MARS = 1
        private const val TYPE_HEADER = 2
    }
}

