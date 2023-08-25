package com.sparta.week1.adapter.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.sparta.week1.databinding.ItemToDoBinding
import com.sparta.week1.model.TodoModel

class BookmarkedAdapter : RecyclerView.Adapter<BookmarkedAdapter.BookMarkedViewHolder>() {
    private val data = ArrayList<TodoModel>()
    fun addItems(items: List<TodoModel>) {
        data.clear()
        data.addAll(items.filter{ it.isChecked })
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookMarkedViewHolder {
        Log.i(TAG, "onCreateViewHolder - ToDoBookmarked")
        return BookMarkedViewHolder(
            ItemToDoBinding
                .inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
        )
    }


    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: BookMarkedViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder - ToDoBookmarked $position")
        holder.bind(data[position])
    }

    inner class BookMarkedViewHolder(private val binding: ItemToDoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: TodoModel) {
            binding.textViewToDo.text = data.title
            binding.textViewDescription.text = data.description
            binding.checkbox.isVisible = false
        }
    }

    companion object {
        const val TAG = "ToDoAdapter"
    }
}