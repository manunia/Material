package com.example.material.ui.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.material.R
import kotlinx.android.synthetic.main.activity_recycler_item_earth.view.*
import kotlinx.android.synthetic.main.activity_recycler_item_mars.view.*


class RecyclerActivityAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: MutableList<Pair<Data, Boolean>>,
    private val dragListener: OnStartDragListener
) :
    RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {

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
            data[position].first.someDescription.isNullOrBlank() -> TYPE_MARS
            else -> TYPE_EARTH
        }
    }

    inner class EarthViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<Data, Boolean>) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                //itemView.descriptionTextView.text = data.first.someDescription
                itemView.earthImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(
                        data.first
                    )
                }
            }

            itemView.earth_add_button.setOnClickListener {
                addItem()
            }

            itemView.earth_remove_button.setOnClickListener {
                removeItem()
            }

            itemView.earthDescriptionTextView.visibility = if (data.second) View.VISIBLE else View.GONE
            itemView.earthTextView.setOnClickListener {
                toggleText()
            }

            itemView.earth_dragHandleImageView.setOnTouchListener { _, event ->
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(this)
                }
                false
            }

        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)

        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }

        private fun addItem() {
            data.add(layoutPosition, generateNote())
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun toggleText() {
            data[layoutPosition] = data[layoutPosition].let {
                it.first to !it.second
            }

            notifyItemChanged(layoutPosition)
        }

    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<Data, Boolean>) {
            itemView.marsImageView.setOnClickListener {
                onListItemClickListener.onItemClick(
                    data
                        .first
                )
            }

            itemView.mars_add_button.setOnClickListener {
                addItem()
            }

            itemView.mars_remove_button.setOnClickListener {
                removeItem()
            }

            itemView.marsDescriptionTextView.visibility = if (data.second) View.VISIBLE else View.GONE
            itemView.marsTextView.setOnClickListener {
                toggleText()
            }

            itemView.dragHandleImageView.setOnTouchListener { _, event ->
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(this)
                }
                false
            }


        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.YELLOW)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }

        private fun addItem() {
            data.add(layoutPosition, generateImportantNote())
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }
        
        private fun toggleText() {
            data[layoutPosition] = data[layoutPosition].let {
                it.first to !it.second
            }

            notifyItemChanged(layoutPosition)
        }

    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<Data, Boolean>) {
            itemView.setOnClickListener { onListItemClickListener.onItemClick(data.first) }
        }

        override fun onItemSelected() {
            notifyDataSetChanged()
        }

        override fun onItemClear() {

        }
    }

    private fun generateImportantNote() = Pair(Data("Mars", ""), false)
    private fun generateNote() = Pair(Data("Earth", ""), false)

    fun appendItem() {
        data.add(generateImportantNote())
        notifyDataSetChanged()
    }

    interface OnListItemClickListener {
        fun onItemClick(data: Data)
    }

    interface OnStartDragListener {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    }

    companion object {
        private const val TYPE_EARTH = 0
        private const val TYPE_MARS = 1
        private const val TYPE_HEADER = 2
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        data.removeAt(fromPosition).apply {
            data.add(if (toPosition > fromPosition) toPosition - 1
                else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }
}

