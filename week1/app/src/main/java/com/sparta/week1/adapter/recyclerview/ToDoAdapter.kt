package com.sparta.week1.adapter.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sparta.week1.databinding.ItemToDoBinding

class ToDoAdapter(
    private val data: List<String>
) : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        Log.i(TAG, "onCreateViewHolder - ToDo")
        return ToDoViewHolder(
            ItemToDoBinding
                .inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder - ToDo $position")
        holder.bind(data[position])
    }

    inner class ToDoViewHolder(private val binding: ItemToDoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: String) {
            binding.textViewToDo.text = data
        }
    }

    companion object {
        const val TAG = "ToDoAdapter"
    }
}