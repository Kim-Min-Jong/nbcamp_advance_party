package com.sparta.week1

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.sparta.week1.adapter.FragmentAdapter
import com.sparta.week1.databinding.ActivityMainBinding
import com.sparta.week1.model.TodoModel
import java.util.UUID

// AppCompatActivity는 FragmentActivity를 상속받음
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val callback by lazy {
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.fabAddWriting.show()
                    else -> binding.fabAddWriting.hide()
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
                val title = it.data?.getStringExtra("title") ?: ""
                val desc = it.data?.getStringExtra("desc") ?: ""
                list.add(TodoModel(UUID.randomUUID().toString(), title, desc, false))
            }
        }

    private lateinit var bundle: Bundle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("activity oncreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initButton()
    }

    private fun initViews() = with(binding) {
        pager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, pager) { tab, pos ->
            tab.text = titleList[pos]
        }.attach()
        pager.registerOnPageChangeCallback(callback)
    }

    private fun updateList() = with(binding) {
//        pager.adapter = viewPagerAdapter.apply {
        val todoFragment = viewPagerAdapter.getFragments(R.string.to_do)?.fragment as ToDoFragment
        val bookMarkedFragment =
            viewPagerAdapter.getFragments(R.string.to_do_bookmarked)?.fragment as BookmarkedToDoFragment
        todoFragment.setList(list)
        bookMarkedFragment.setList(list.filter { it.isChecked })
    }

    private fun initButton() = with(binding) {
        fabAddWriting.setOnClickListener {
            activityLauncher.launch(
                Intent(this@MainActivity, TodoWritingActivity::class.java)
            )
        }
    }

    override fun onDestroy() = with(binding) {
        println("activity destroy")
        pager.unregisterOnPageChangeCallback(callback)
        super.onDestroy()
    }

    override fun onRestart() {
        println("activity restart")
        super.onRestart()
    }


    override fun onResume() {
        println("activity resume")
        println(list)
        updateList()
        super.onResume()
    }

    override fun onPause() {
        println("activity pause")
        super.onPause()
    }

    override fun onStop() {
        println("activity stop")
        super.onStop()
    }

    override fun onStart() {
        println("activity onstart")
        super.onStart()
    }

    companion object {
        val list = arrayListOf<TodoModel>()
        fun newIntent(context: Context): Intent =
            Intent(context, MainActivity::class.java)
    }
}