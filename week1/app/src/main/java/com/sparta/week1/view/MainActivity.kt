package com.sparta.week1.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.sparta.week1.view.bookmark.BookmarkedToDoFragment
import com.sparta.week1.R
import com.sparta.week1.view.todo.ToDoFragment
import com.sparta.week1.view.todo.add.TodoContentActivity.Companion.EXTRA_TODO_MODEL
import com.sparta.week1.adapter.FragmentAdapter
import com.sparta.week1.databinding.ActivityMainBinding
import com.sparta.week1.model.TodoModel
import com.sparta.week1.view.todo.add.TodoContentActivity
import com.sparta.week1.view.todo.add.TodoContentActivity.Companion.EXTRA_TYPE
import com.sparta.week1.view.type.TodoContentType

// AppCompatActivity는 FragmentActivity를 상속받음
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var todoModel: TodoModel? = null
    private val callback by lazy {
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (viewPagerAdapter.getFragments(position)) {
                    is ToDoFragment -> binding.fabAddWriting.show()
                    is BookmarkedToDoFragment -> binding.fabAddWriting.hide()
                }
            }
        }
    }
    private val viewPagerAdapter by lazy {
        FragmentAdapter(this@MainActivity)
    }

    private val titleList by lazy {
        listOf(
            getString(R.string.to_do),
            getString(R.string.to_do_bookmarked)
        )
    }
    private val _activityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                todoModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.data?.getParcelableExtra(EXTRA_TODO_MODEL, TodoModel::class.java)
                } else {
                    it.data?.getParcelableExtra(EXTRA_TODO_MODEL)
                }
                val todoFragment = viewPagerAdapter.getFragmentsById(R.string.to_do) as? ToDoFragment
                todoFragment?.addContent(todoModel)
                todoFragment?.setOnClickListener()
            }
        }
    val activityLauncher: ActivityResultLauncher<Intent>
        get() = _activityLauncher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initButton()
    }

    private fun initViews() = with(binding) {
        pager.run {
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(callback)
        }
        TabLayoutMediator(tabLayout, pager) { tab, pos ->
            tab.text = titleList[pos]
        }.attach()
    }


    private fun initButton() = with(binding) {
        fabAddWriting.setOnClickListener {
            activityLauncher.launch(
                TodoContentActivity.newIntentForAdd(this@MainActivity)
            )
        }
    }

    override fun onDestroy() = with(binding) {
        // 프래그먼트단으로 이동
        pager.unregisterOnPageChangeCallback(callback)
        super.onDestroy()
    }

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, MainActivity::class.java)

    }
}