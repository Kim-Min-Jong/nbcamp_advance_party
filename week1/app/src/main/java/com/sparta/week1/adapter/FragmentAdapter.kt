package com.sparta.week1.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.sparta.week1.BookmarkedToDoFragment
import com.sparta.week1.ToDoFragment

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        // 그냥 호출이 아니라 return을 해줘야함 중요
       when(position) {
           0 -> {
               return ToDoFragment()
           }
           1 -> {
               return BookmarkedToDoFragment()
           }
       }
        return ToDoFragment()
    }
}