package com.sparta.week1.adapter

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.sparta.week1.BookmarkedToDoFragment
import com.sparta.week1.R
import com.sparta.week1.ToDoFragment
import com.sparta.week1.model.MainTabs
import com.sparta.week1.model.TodoModel

class FragmentAdapter(fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity) {
    private val fragments = ArrayList<MainTabs>()
    init {
        fragments.add(
            MainTabs(ToDoFragment.newInstance(), R.string.to_do)
        )
        fragments.add(
            MainTabs(BookmarkedToDoFragment.newInstance(), R.string.to_do_bookmarked),
        )
    }
    fun getFragments(@StringRes id: Int) = fragments.find{ it.titleRes == id }
    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
//        bundle = item
        println("viewpager bindview")
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int = fragments.size
    
    override fun createFragment(position: Int): Fragment =
        // 그냥 호출이 아니라 return을 해줘야함 중요
      when(position) {
           0 -> {
               println("ToDoFragment ViewPager create")
               fragments[position].fragment
           }
           else ->{
               println("BookMarkedToDoFragment ViewPager create")
               fragments[position].fragment
           }
       }

    companion object {
        const val TAG = "Fragment Adpater"
    }
}