package com.sparta.week1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.sparta.week1.adapter.FragmentAdapter
import com.sparta.week1.databinding.ActivityMainBinding

// AppCompatActivity는 FragmentActivity를 상속받음
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val titleList by lazy {
        listOf(
            getString(R.string.to_do),
            getString(R.string.to_do_bookmarked)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initButton()
    }


    private fun initViews() = with(binding) {
        pager.adapter = FragmentAdapter(this@MainActivity)
//        pager.adapter = FragmentAdapter(supportFragmentManager, lifecycle)


        TabLayoutMediator(tabLayout, pager) { tab, pos ->
            tab.text = titleList[pos]
        }.attach()
        pager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> fabAddWriting.show()
                    else -> fabAddWriting.hide()
                }
            }
        })

    }

    private fun initButton() = with(binding) {
        fabAddWriting.setOnClickListener {
            startActivity(Intent(this@MainActivity, TodoWritingActivity::class.java))
        }
    }
}