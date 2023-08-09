package com.sparta.week1.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.sparta.week1.BookmarkedToDoFragment
import com.sparta.week1.ToDoFragment

class FragmentAdapter(fragmentActivity: FragmentActivity)
// class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentActivity) {
//    : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        // 그냥 호출이 아니라 return을 해줘야함 중요
       when(position) {
           0 -> {
               Log.i(TAG, "ToDoFragment ViewPager")
               return ToDoFragment()
           }
           1 -> {
               Log.i(TAG, "BookmarkedToDoFragment ViewPager")
               return BookmarkedToDoFragment()
           }
       }
        return ToDoFragment()
    }
    companion object {
        const val TAG = "Fragment Adpater"
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        Log.i(TAG, "onBindViewHolder ViewPager")

    }
}