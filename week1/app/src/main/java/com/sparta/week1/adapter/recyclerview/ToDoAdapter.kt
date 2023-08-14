package com.sparta.week1.adapter.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sparta.week1.databinding.ItemToDoBinding
import com.sparta.week1.model.TodoModel

class ToDoAdapter : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {
    private val data = ArrayList<TodoModel>()

    fun addItem(item: TodoModel) {
        data.add(item)
        notifyDataSetChanged()
    }
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

        fun bind(data: TodoModel) = with(binding) {
            textViewToDo.text = data.title
            textViewDescription.text = data.description
        }
    }

    companion object {
        const val TAG = "ToDoAdapter"
    }
}