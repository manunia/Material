package com.example.material.ui.apibottom

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.example.material.R
import com.example.material.ui.api.EarthFragment
import com.example.material.ui.api.MarsFragment
import com.example.material.ui.api.WeatherFragment
import kotlinx.android.synthetic.main.activity_api_bottom.*
import kotlinx.android.synthetic.main.fragment_earth.*

class ApiBottomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api_bottom)
        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_today -> {
                    replaceFragment(EarthFragment())
                    true
                }
                R.id.bottom_view_mars -> {
                    replaceFragment(MarsFragment())
                    true
                }
                R.id.bottom_view_weather -> {
                    replaceFragment(WeatherFragment())
                    true
                }
                else -> {
                    replaceFragment(EarthFragment())
                    true
                }
            }
        }
        bottom_navigation_view.selectedItemId = R.id.bottom_view_today

        bottom_navigation_view.setOnNavigationItemReselectedListener { item ->
            when(item.itemId) {
                R.id.bottom_view_today -> {

                }
                R.id.bottom_view_mars -> {

                }
                R.id.bottom_view_weather -> {

                }
            }
        }

        bottom_navigation_view.getOrCreateBadge(R.id.bottom_view_today)
        val bage = bottom_navigation_view.getBadge(R.id.bottom_view_today)
        bage?.maxCharacterCount = 2
        bage?.number = 999




    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_api_bottom_container, fragment)
            .commitAllowingStateLoss()
    }
}