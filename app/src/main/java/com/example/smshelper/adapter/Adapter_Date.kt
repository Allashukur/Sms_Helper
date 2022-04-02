package com.example.smshelper.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smshelper.data_base_room.entity.Date_model
import com.example.smshelper.databinding.ItemDateBinding

class Adapter_Date(var onClickListenerDate: OnClickListenerDate) :
    ListAdapter<Date_model, Adapter_Date.ViewHolder>(MyDiffUtill()) {

    class MyDiffUtill : DiffUtil.ItemCallback<Date_model>() {
        override fun areItemsTheSame(oldItem: Date_model, newItem: Date_model): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Date_model, newItem: Date_model): Boolean {
            return oldItem == newItem
        }

    }

    inner class ViewHolder(var itemDateBinding: ItemDateBinding) :
        RecyclerView.ViewHolder(itemDateBinding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemDateBinding.dateId.setText(getItem(position).date)
        holder.itemDateBinding.cardViewItem.setOnClickListener {
            onClickListenerDate.onClickDate(getItem(position).id)
        }

    }

    interface OnClickListenerDate {
        fun onClickDate(dateId: Int)
    }

}