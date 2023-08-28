package com.sparta.week1.adapter

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.sparta.week1.BookmarkedToDoFragment
import com.sparta.week1.R
import com.sparta.week1.ToDoFragment
import com.sparta.week1.model.MainTabs

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
    fun getFragments(position: Int) = fragments[position].fragment
    fun getFragmentsById(@StringRes id: Int) = fragments.find{ it.titleRes == id }?.fragment
    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int = fragments.size
    
    override fun createFragment(position: Int): Fragment =
        fragments[position].fragment

    companion object {
        const val TAG = "Fragment Adpater"
    }
}