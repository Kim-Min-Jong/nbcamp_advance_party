package com.sparta.week1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sparta.week1.adapter.recyclerview.BookmarkedAdapter
import com.sparta.week1.databinding.FragmentBookmarkedToDoBinding
import com.sparta.week1.model.BookmarkedTodoModel

class BookmarkedToDoFragment : Fragment() {
    private var _binding: FragmentBookmarkedToDoBinding? = null
    private val binding
        get() = _binding!!
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
            val testList = arrayListOf<BookmarkedTodoModel>()
            for (i in 0 until 30) {
                testList.add(
                    BookmarkedTodoModel(
                        "Bookmark Title $i",
                        "desc $i"
                    )
                )
            }

            bookmarkedRecyclerView.adapter = BookmarkedAdapter().apply {
                addItems(testList)
            }
            bookmarkedRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        fun newInstance() = BookmarkedToDoFragment()
    }
}