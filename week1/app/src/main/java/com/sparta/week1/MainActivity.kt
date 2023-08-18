package com.sparta.week1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.sparta.week1.adapter.FragmentAdapter
import com.sparta.week1.databinding.ActivityMainBinding
import com.sparta.week1.model.TodoModel

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
        FragmentAdapter(this@MainActivity, list)
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
                list.add(TodoModel(list.size+1, title, desc))
            }
        }

    private lateinit var bundle: Bundle
    private val list = arrayListOf<TodoModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("activity oncreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews(emptyList())
        initButton()
    }

    private fun initViews(list: List<TodoModel>) = with(binding) {
        pager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, pager) { tab, pos ->
            tab.text = titleList[pos]
        }.attach()
        pager.registerOnPageChangeCallback(callback)
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
        initViews(list)
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
}