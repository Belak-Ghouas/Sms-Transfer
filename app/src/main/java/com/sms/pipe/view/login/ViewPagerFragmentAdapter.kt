package com.sms.pipe.view.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerFragmentAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val list: ArrayList<Fragment> = ArrayList()
    private val pageIds = list.map { it.hashCode().toLong() }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addFragment(fragment: Fragment) {
        list.add(fragment)
        notifyDataSetChanged()
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }

    fun removeFragment(position: Int) {
        if (position < list.size) {
            list.removeAt(position)
            notifyDataSetChanged()
        }
    }

    fun addFragment(fragment: Fragment, position: Int) {
        list.add(position, fragment)
        notifyItemInserted(position)
    }

    /**
     * we did this to make the The [FragmentStateAdapter] more dynamic
     * when we add and remove items
     */
    override fun getItemId(position: Int): Long {
        return list[position].hashCode().toLong() // make sure notifyDataSetChanged() works
    }

    override fun containsItem(itemId: Long): Boolean {
        return pageIds.contains(itemId)
    }


}