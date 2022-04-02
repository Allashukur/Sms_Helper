package com.example.smshelper.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smshelper.data_base_room.entity.Province_model
import com.example.smshelper.databinding.ItemProvinceBinding

class Adapter_Province(var onClickListenerProvince: OnClickListenerDate) :
    ListAdapter<Province_model, Adapter_Province.ViewHolder>(MyDiffUtill()) {

    class MyDiffUtill : DiffUtil.ItemCallback<Province_model>() {
        override fun areItemsTheSame(oldItem: Province_model, newItem: Province_model): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Province_model, newItem: Province_model): Boolean {
            return oldItem == newItem
        }

    }

    inner class ViewHolder(var itemDateBinding: ItemProvinceBinding) :
        RecyclerView.ViewHolder(itemDateBinding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProvinceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemDateBinding.provinceText.setText(getItem(position).province_name.toString())
        holder.itemDateBinding.cardViewItem1.setOnClickListener {
            onClickListenerProvince.onClickDate(getItem(position).id)
        }

    }

    interface OnClickListenerDate {
        fun onClickDate(dateId: Int)
    }

}