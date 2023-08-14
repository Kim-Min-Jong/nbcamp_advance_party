package com.sparta.week1.adapter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.sparta.week1.BookmarkedToDoFragment
import com.sparta.week1.R
import com.sparta.week1.ToDoFragment
import com.sparta.week1.model.MainTabs

class FragmentAdapter(fragmentActivity: FragmentActivity, private val item: Bundle)
    : FragmentStateAdapter(fragmentActivity) {

    private lateinit var bundle: Bundle
    private val _fragments = ArrayList<MainTabs>()
    val fragments: List<MainTabs>
        get() = _fragments
    init {
        _fragments.add(
            MainTabs(ToDoFragment.newInstance(), R.string.to_do)
        )
        _fragments.add(
            MainTabs(BookmarkedToDoFragment.newInstance(), R.string.to_do_bookmarked),
        )
    }
    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        bundle = item
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int = _fragments.size

    override fun createFragment(position: Int): Fragment =
        // 그냥 호출이 아니라 return을 해줘야함 중요
      when(position) {
           0 -> {
               Log.i(TAG, "ToDoFragment ViewPager")
               _fragments[position].fragment.apply {
                   if(bundle.isEmpty){
                       return@apply
                   }
                   val bundle = Bundle().apply {
                       putString("title", bundle.getString("title"))
                       putString("desc", bundle.getString("desc"))
                   }
                   arguments = bundle
               }
           }
           else -> _fragments[position].fragment
       }


    companion object {
        const val TAG = "Fragment Adpater"
    }
}