package com.sparta.week1.adapter.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sparta.week1.databinding.ItemToDoBookmarkedBinding
import com.sparta.week1.model.BookmarkedTodoModel

class BookmarkedAdapter : RecyclerView.Adapter<BookmarkedAdapter.BookMarkedViewHolder>() {
    private val data =  ArrayList<BookmarkedTodoModel>()
    fun addItems(items: List<BookmarkedTodoModel>) {
        data.addAll(items)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookMarkedViewHolder {
        Log.i(TAG, "onCreateViewHolder - ToDoBookmarked")
        return BookMarkedViewHolder(
            ItemToDoBookmarkedBinding
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

    inner class BookMarkedViewHolder(private val binding: ItemToDoBookmarkedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: BookmarkedTodoModel) {
            binding.textViewToDoBookmarked.text = data.title
        }
    }

    companion object {
        const val TAG = "ToDoAdapter"
    }
}