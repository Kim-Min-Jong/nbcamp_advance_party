package com.sparta.week1.adapter.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sparta.week1.databinding.ItemToDoBinding
import com.sparta.week1.model.TodoModel

class ToDoAdapter : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {
    private val modelList = ArrayList<TodoModel>()

    fun addItem(item: TodoModel) {
        modelList.add(item)
        notifyDataSetChanged()
//        notifyItemChanged(modelList.size - 1)
    }

    fun getItems() = modelList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder = ToDoViewHolder(
            ItemToDoBinding
                .inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
        )

    override fun getItemCount(): Int = modelList.size
    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder - ToDo $position")
        holder.bind(modelList[position])
    }

    inner class ToDoViewHolder(private val binding: ItemToDoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: TodoModel) = with(binding) {
            textViewToDo.text = data.title
            textViewDescription.text = data.description
            checkbox.isChecked = data.isChecked
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                modelList.find { it.id == data.id }?.isChecked = isChecked
            }
        }
    }

    companion object {
        const val TAG = "ToDoAdapter"
    }
}