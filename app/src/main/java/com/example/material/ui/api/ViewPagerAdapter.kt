package com.example.material.ui.api

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

private const val EARTH_FRAGMENT = 0
private const val MARS_FRAGMENT = 1
private const val WEATHER_FRAGMENT = 2

class ViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = arrayOf(EarthFragment(), MarsFragment(), WeatherFragment())

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> fragments[EARTH_FRAGMENT] as Fragment
            1 -> fragments[MARS_FRAGMENT] as Fragment
            2 -> fragments[WEATHER_FRAGMENT] as Fragment
            else -> fragments[EARTH_FRAGMENT] as Fragment
        }
    }

    override fun getCount(): Int {
        return fragments.size
    }

//    override fun getPageTitle(position: Int): CharSequence? {
//        return when (position) {
//            0 -> "Earth"
//            1 -> "Mars"
//            2 -> "Weather"
//            else -> "Earth"
//        }
//    }



}