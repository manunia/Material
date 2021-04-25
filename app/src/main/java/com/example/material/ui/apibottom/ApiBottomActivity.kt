package com.example.material.ui.apibottom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.material.R
import kotlinx.android.synthetic.main.activity_api_bottom.*

class ApiBottomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api_bottom)
        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_earth -> {
                    //Item tapped
                    true
                }
                R.id.bottom_view_mars -> {
                    //Item tapped
                    true
                }
                R.id.bottom_view_weather -> {
                    //Item tapped
                    true
                }
                else -> false
            }
        }
    }
}