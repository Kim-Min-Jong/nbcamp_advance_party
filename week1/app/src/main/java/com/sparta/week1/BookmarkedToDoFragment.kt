package com.sparta.week1

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkedToDoBinding.inflate(layoutInflater)
        list = getList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initRecyclerView() = with(binding) {

        bookmarkedRecyclerView.adapter = BookmarkedAdapter().apply {
            addItems(list ?: emptyList())
        }
        bookmarkedRecyclerView.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun getList(): List<TodoModel>? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelableArrayList("list", TodoModel::class.java)
        } else {
            arguments?.getParcelableArrayList("list")
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        println("Booktodo - Attach")
    }

    override fun onDetach() {
        super.onDetach()
        println("Booktodo - Detach")
    }

    override fun onPause() {
        super.onPause()
        println("Booktodo - Pause")
    }

    override fun onStart() {
        super.onStart()
        println("Booktodo - Stop")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        println("Booktodo - Viewstaterestored")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        println("Booktodo - saveInstanceState")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Booktodo - destroy")
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
        println("resume!" + list)
        initRecyclerView()
        println("Booktodo - onResume")
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        println("Booktodo - onStop")
    }

    companion object {
        fun newInstance() = BookmarkedToDoFragment()
    }
}