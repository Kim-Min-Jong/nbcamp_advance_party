package com.sparta.week1.view.bookmark

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.sparta.week1.adapter.recyclerview.BookmarkedAdapter
import com.sparta.week1.databinding.FragmentBookmarkedToDoBinding
import com.sparta.week1.model.TodoModel

class BookmarkedToDoFragment : Fragment() {
    private var _binding: FragmentBookmarkedToDoBinding? = null
    private var list: List<TodoModel>? = emptyList()
    private val binding
        get() = _binding!!
    private val listAdapter by lazy {
        BookmarkedAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkedToDoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initRecyclerView() = with(binding) {
        bookmarkedRecyclerView.adapter = listAdapter
        bookmarkedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onResume() {
        // 안됨
//        list = getList()
        parentFragmentManager.setFragmentResultListener(
            "newList",
            viewLifecycleOwner
        ) { _, bundle ->
            list = bundle.getParcelableArrayList("list", TodoModel::class.java)
        }
        listAdapter.addItems(list ?: arrayListOf())
        println("Booktodo - onResume")
        super.onResume()
    }

    companion object {
        fun newInstance() = BookmarkedToDoFragment()
    }
}