package com.example.cattlelog.main_activity

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.cattlelog.herd_member_details.health_tab.HealthFragment
import com.example.cattlelog.main_activity.herd_tab.HerdFragment

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class TabPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val fragment: Fragment = when (position) {
            Tab.HERD.position -> HerdFragment()
            Tab.NEXT_HEATS.position -> HealthFragment()
            // So Kotlin will stop complaining >:(
            else -> HerdFragment()
        }

        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(Tab[position]!!.title)
    }

    // Return the number of tabs
    override fun getCount(): Int {
        return Tab.values().size
    }
}