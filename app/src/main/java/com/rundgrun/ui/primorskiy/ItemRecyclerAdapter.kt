package com.rundgrun.ui.primorskiy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rundgrun.databinding.ItemBinding
import com.rundgrun.ui.models.Item


interface ItemActionListeners {
    fun onItemDetails(item: Item)
}

class InstrumentRecyclerAdapter(
    private val actionListeners: ItemActionListeners
) :
    RecyclerView.Adapter<InstrumentRecyclerAdapter.InstrumentHolder>(),
    View.OnClickListener {

    var items = emptyList<Item>()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }
    override fun onClick(v: View) {
        val instrument = v.tag as Item
        actionListeners.onItemDetails(instrument)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstrumentHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)

        return InstrumentHolder(binding)
    }

    override fun onBindViewHolder(holder: InstrumentHolder, position: Int) {
        val item = items[position]
        holder.itemView.tag = item
        with(holder.binding) {
            id.text = item.id
            description.text = item.description
        }
    }

    override fun getItemCount(): Int = items.size

    class InstrumentHolder(val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}