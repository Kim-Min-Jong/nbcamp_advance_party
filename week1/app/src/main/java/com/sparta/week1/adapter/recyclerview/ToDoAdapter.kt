package com.sparta.week1.adapter.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sparta.week1.adapter.listener.ItemClickListener
import com.sparta.week1.databinding.ItemToDoBinding
import com.sparta.week1.model.TodoModel

class ToDoAdapter : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {
    private val modelList = ArrayList<TodoModel>()
    private lateinit var itemClickListener: ItemClickListener

    fun addItem(item: TodoModel) {
        modelList.add(item)
        notifyDataSetChanged()
//        notifyItemChanged(modelList.size - 1)
    }

    fun updateItem(item: TodoModel) {
        modelList.find { it.id == item.id }?.apply {
            title = item.title
            description = item.description
        }
        notifyDataSetChanged()
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

    fun setOnItemClickListener(listener: ItemClickListener) {
        itemClickListener = listener
    }

    inner class ToDoViewHolder(private val binding: ItemToDoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(adapterPosition)
            }
        }
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