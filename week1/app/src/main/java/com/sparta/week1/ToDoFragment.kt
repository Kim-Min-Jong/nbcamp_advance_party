package com.sparta.week1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sparta.week1.adapter.recyclerview.BookmarkedAdapter
import com.sparta.week1.adapter.recyclerview.ToDoAdapter
import com.sparta.week1.databinding.FragmentToDoBinding

class ToDoFragment : Fragment() {
    private var binding: FragmentToDoBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToDoBinding.inflate(layoutInflater)
        initRecyclerView()
        return binding?.root
    }

    private fun initRecyclerView() = binding?.let {
        with(it) {
            val list = mutableListOf<String>()
            (0..30).forEach { idx ->
                list.add("${getString(R.string.to_do)} ${idx + 1}")
            }
            toDoRecyclerView.adapter = ToDoAdapter(list)
            toDoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}