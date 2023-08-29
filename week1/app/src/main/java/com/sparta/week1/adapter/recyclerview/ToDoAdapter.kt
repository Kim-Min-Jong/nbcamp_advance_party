package com.sparta.week1.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sparta.week1.adapter.listener.ItemClickListener
import com.sparta.week1.databinding.ItemToDoBinding
import com.sparta.week1.model.TodoModel

class ToDoAdapter : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {
    private val _modelList = ArrayList<TodoModel>()
    val modelList: List<TodoModel>
        get() = _modelList
    val newList = _modelList // 위아래는 같은 뜻?

    private lateinit var itemClickListener: ItemClickListener

    fun addItem(item: TodoModel) {
        _modelList.add(item)
        notifyItemChanged(modelList.size - 1)
    }

    fun updateItem(item: TodoModel) {
        _modelList.find { it.id == item.id }?.apply {
            title = item.title
            description = item.description
        }
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder = ToDoViewHolder(
            ItemToDoBinding
                .inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
        )

    override fun getItemCount(): Int = _modelList.size
    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) =
        holder.bind(_modelList[position])


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
                _modelList.find { it.id == data.id }?.isChecked = isChecked
            }
        }
    }

    companion object {
        const val TAG = "ToDoAdapter"
    }
}