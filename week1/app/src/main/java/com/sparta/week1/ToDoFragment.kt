package com.sparta.week1

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sparta.week1.adapter.recyclerview.ToDoAdapter
import com.sparta.week1.databinding.FragmentToDoBinding
import com.sparta.week1.model.TodoModel
import java.util.ArrayList


class ToDoFragment : Fragment() {
    private var _binding: FragmentToDoBinding? = null
    private val binding
        get() = _binding!!
    private val listAdapter by lazy {
        ToDoAdapter()
    }
    private lateinit var title: String
    private lateinit var description: String
    private var list: List<TodoModel>? = emptyList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToDoBinding.inflate(layoutInflater)
        list = getList()
        println("todo - CreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        println("todo - ViewCreated")
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initRecyclerView() = with(binding) {
        toDoRecyclerView.adapter = listAdapter.apply {
            addItems(list ?: emptyList())
        }
        toDoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
    private fun getList(): List<TodoModel>? {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelableArrayList("list", TodoModel::class.java)
        } else {
            arguments?.getParcelableArrayList("list")
        }
    }

    override fun onDestroyView() {
        _binding = null
        println("todo - DestroyView")
        super.onDestroyView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        println("todo - Attach")
    }

    override fun onDetach() {
        super.onDetach()
        println("todo - Detach")
    }

    override fun onPause() {
        super.onPause()
        println("pause"+list)
        val bundle = Bundle().apply {
            putParcelableArrayList("list", list as ArrayList<out Parcelable>)
        }
// --> 불가능 왜?
//        arguments = bundle

        parentFragmentManager.setFragmentResult("newList", bundle)
        println("todo - Pause")
    }

    override fun onStart() {
        super.onStart()
        println("todo - Stop")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        println("todo - Viewstaterestored")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        println("todo - saveInstanceState")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("todo - destroy")
    }
    override fun onResume() {
        println("todo - onResume")
        super.onResume()

    }

    override fun onStop() {
        super.onStop()
        println("todo - onStop")
    }
    companion object {
        fun newInstance() = ToDoFragment()
    }
}