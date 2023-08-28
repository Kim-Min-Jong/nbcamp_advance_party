package com.sparta.week1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sparta.week1.adapter.recyclerview.ToDoAdapter
import com.sparta.week1.databinding.FragmentToDoBinding
import com.sparta.week1.model.TodoModel


class ToDoFragment : Fragment() {
    private var _binding: FragmentToDoBinding? = null
    private val binding
        get() = _binding!!
    private val listAdapter by lazy {
        ToDoAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToDoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initRecyclerView() = with(binding) {
        toDoRecyclerView.run {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    fun addContent(item: TodoModel?) {
        if(item == null){
            return
        }
        listAdapter.addItem(item)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
    override fun onPause() {
        super.onPause()
        val bundle = Bundle().apply {
            putParcelableArrayList("list", listAdapter.getItems())
        }
// --> 불가능 왜? 이걸로는 bookmark fragment로 전송자체를 못하나?
//        arguments = bundle

        parentFragmentManager.setFragmentResult("newList", bundle)
    }
    companion object {
        fun newInstance() = ToDoFragment()
    }
}