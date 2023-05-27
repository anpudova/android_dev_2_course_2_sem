package com.example.tasksproject.presentation.adapter

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tasksproject.db.model.CityModel
import com.example.tasksproject.presentation.screen.fragment.ItemPageFragment

class CityViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    var fragmentList: ArrayList<ItemPageFragment> = arrayListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = fragmentList.size

    fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCities(cities: List<CityModel?>?) {
        if (cities != null) {
            fragmentList.clear()
            for(i in cities.indices) {
                cities[i].let {
                    fragmentList.add(ItemPageFragment(it))
                }
            }
        }
        notifyDataSetChanged()
    }
}
