package com.example.tasksproject.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksproject.databinding.ItemRecyclerViewBinding

class VisibilityListenerTestAdapter: RecyclerView.Adapter<VisibilityListenerTestAdapter.ItemViewHolder>() {

    var items: ArrayList<String> = arrayListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            _binding = ItemRecyclerViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ItemViewHolder(
        private var _binding: ItemRecyclerViewBinding
    ) : RecyclerView.ViewHolder(_binding.root) {


        fun bindItem(item: String) {
            with(_binding) {
                tvName.text = item
            }

        }
    }
}