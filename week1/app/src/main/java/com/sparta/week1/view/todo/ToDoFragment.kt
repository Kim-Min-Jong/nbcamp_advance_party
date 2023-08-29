package com.sparta.week1.view.todo

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.sparta.week1.adapter.listener.ItemClickListener
import com.sparta.week1.adapter.recyclerview.ToDoAdapter
import com.sparta.week1.databinding.FragmentToDoBinding
import com.sparta.week1.model.TodoModel
import com.sparta.week1.view.MainActivity
import com.sparta.week1.view.todo.add.TodoContentActivity
import java.util.ArrayList


class ToDoFragment : Fragment() {
    private var _binding: FragmentToDoBinding? = null
    private var _mainActivity: MainActivity? = null
    private val binding
        get() = _binding!!
    private val mainActivity
        get() = _mainActivity!!
    private val listAdapter by lazy {
        ToDoAdapter()
    }
    private val editToDoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val todoModel: TodoModel? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra(
                        TodoContentActivity.EXTRA_TODO_MODEL,
                        TodoModel::class.java
                    )
                } else {
                    result.data?.getParcelableExtra(
                        TodoContentActivity.EXTRA_TODO_MODEL
                    )
                }
                updateContent(todoModel)
            }
        }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        _mainActivity = context as MainActivity
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
        if (item == null) {
            return
        }
        listAdapter.addItem(item)
    }
    private fun updateContent(item: TodoModel?) {
        if (item == null) {
            return
        }
        listAdapter.updateItem(item)
    }

    fun setOnClickListener() {
        listAdapter.setOnItemClickListener(object : ItemClickListener {
            override fun onItemClick(position: Int) {
                editToDoLauncher.launch(
                    TodoContentActivity.newIntentForEdit(
                        requireContext(),
                        listAdapter.modelList[position]
                    )
                )
            }
        })
    }

    override fun onDestroyView() {
        _binding = null
        _mainActivity = null
        super.onDestroyView()
    }

    override fun onPause() {
        super.onPause()
        val bundle = Bundle().apply {
            putParcelableArrayList("list", listAdapter.modelList as ArrayList<out Parcelable>)
        }
// --> 불가능 왜? 이걸로는 bookmark fragment로 전송자체를 못하나?
//        arguments = bundle

        parentFragmentManager.setFragmentResult("newList", bundle)
    }



    companion object {
        fun newInstance() = ToDoFragment()
    }
}