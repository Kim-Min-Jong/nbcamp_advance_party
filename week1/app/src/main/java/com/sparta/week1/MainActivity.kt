package com.sparta.week1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.sparta.week1.TodoWritingActivity.Companion.EXTRA_TODO_MODEL
import com.sparta.week1.adapter.FragmentAdapter
import com.sparta.week1.databinding.ActivityMainBinding
import com.sparta.week1.model.TodoModel

// AppCompatActivity는 FragmentActivity를 상속받음
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
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
    private val activityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val todoModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.data?.getParcelableExtra(EXTRA_TODO_MODEL, TodoModel::class.java)
                } else {
                    it.data?.getParcelableExtra(EXTRA_TODO_MODEL)
                }
                val todoFragment = viewPagerAdapter.getFragmentsById(R.string.to_do) as? ToDoFragment
                todoFragment?.addContent(todoModel)
            }
        }
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
                Intent(this@MainActivity, TodoWritingActivity::class.java)
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