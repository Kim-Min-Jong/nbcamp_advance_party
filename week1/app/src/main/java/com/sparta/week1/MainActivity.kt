package com.sparta.week1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.sparta.week1.adapter.FragmentAdapter
import com.sparta.week1.databinding.ActivityMainBinding

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
        FragmentAdapter(this@MainActivity, bundle)
    }

    private val titleList by lazy {
        listOf(
            getString(R.string.to_do),
            getString(R.string.to_do_bookmarked)
        )
    }
//    private val activityLauncher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//            if (it.resultCode == Activity.RESULT_OK) {
//                val title = it.data?.getStringExtra("title") ?: ""
//                val desc = it.data?.getStringExtra("desc") ?: ""
//                bundle = Bundle().apply {
//                    putString("title", title)
//                    putString("desc", desc)
//                }
//                println(bundle.toString())
//                binding.pager.adapter = viewPagerAdapter
//            }
//        }

    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initButton()
    }

    private fun initViews() = with(binding) {
                bundle = Bundle().apply {
                    putString("title", intent?.getStringExtra("title") ?: "")
                    putString("desc", intent?.getStringExtra("desc") ?: "")
                }
        pager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, pager) { tab, pos ->
            tab.text = titleList[pos]
        }.attach()
        pager.registerOnPageChangeCallback(callback)
    }

    private fun initButton() = with(binding) {
        fabAddWriting.setOnClickListener {
//            activityLauncher.launch(
//                Intent(this@MainActivity, TodoWritingActivity::class.java)
//            )
            startActivity(Intent(this@MainActivity, TodoWritingActivity::class.java))
        }
    }

    override fun onDestroy() = with(binding) {
        pager.unregisterOnPageChangeCallback(callback)
        super.onDestroy()
    }
}