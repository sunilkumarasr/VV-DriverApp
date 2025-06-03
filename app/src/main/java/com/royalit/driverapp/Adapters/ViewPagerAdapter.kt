package com.royalit.driverapp.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.royalit.driverapp.Fragments.MyOrders.PendingOrdersFragment
import com.royalit.driverapp.Fragments.MyOrders.CompleteOrdersFragment
import com.royalit.driverapp.Fragments.MyOrders.PickUpFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3 // Number of fragments

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PendingOrdersFragment()
            1 -> PickUpFragment()
            2 -> CompleteOrdersFragment()
            else -> PendingOrdersFragment()
        }
    }
}