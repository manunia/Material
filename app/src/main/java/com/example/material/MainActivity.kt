package com.example.material

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.material.ui.chips.*
import com.example.material.ui.picture.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {

    private val NAME_SHARED_PREFERENCE = "LOGIN"
    private val APP_THEME = "APP_THEME"
    private val MARS = 0
    private val EARTH = 1
    private val MOON = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppTheme()
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }

    }

    private fun getAppTheme() {
        val sharedPref = getSharedPreferences(NAME_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val codeStyle = sharedPref.getInt(APP_THEME,1)
        when(codeStyle) {
            MARS -> setTheme(R.style.MarsTheme)
            MOON -> setTheme(R.style.MoonTheme)
            EARTH -> setTheme(R.style.EarthTheme)
            else -> setTheme(R.style.MarsTheme)
        }

    }

}