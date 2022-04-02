package com.example.smshelper.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smshelper.R
import com.example.smshelper.data_base_room.entity.Province_model
import com.example.smshelper.data_base_room.entity.Workers_model
import com.example.smshelper.databinding.ItemProvinceBinding
import com.example.smshelper.databinding.ItemWorkersBinding

class Adapter_Workers(var onClickListener: OnClickListener) :
    ListAdapter<Workers_model, Adapter_Workers.ViewHolder>(MyDiffUtill()) {

    class MyDiffUtill : DiffUtil.ItemCallback<Workers_model>() {
        override fun areItemsTheSame(oldItem: Workers_model, newItem: Workers_model): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Workers_model, newItem: Workers_model): Boolean {
            return oldItem == newItem
        }

    }

    inner class ViewHolder(var itemDateBinding: ItemWorkersBinding) :
        RecyclerView.ViewHolder(itemDateBinding.root) {
        fun onBind(workersModel: Workers_model) {
            itemDateBinding.apply {
                workerName.setText(workersModel.name)
                phonerNumber.setText(workersModel.number)
                message.setText(workersModel.message)
            }
            if (workersModel.working) {
                itemDateBinding.apply {
                    working.setBackgroundResource(R.drawable.card_view_item)
                    workerPosition.setText(workersModel.sms_position.toString())
                }

            } else {
                itemDateBinding.apply {
                    workerPosition.setText("")
                    working.setBackgroundResource(R.drawable.card_view_item2)
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemWorkersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))

        holder.itemView.setOnClickListener {
            onClickListener.onClickDate(getItem(position))
        }

    }

    interface OnClickListener {
        fun onClickDate(worker: Workers_model)
    }

}