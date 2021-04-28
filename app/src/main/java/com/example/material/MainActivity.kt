package com.example.material

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.material.ui.chips.SETTINGS_SHARED_PREFERENCE
import com.example.material.ui.chips.THEME_RES_ID
import com.example.material.ui.picture.PictureOfTheDayFragment

const val MARS_ID = 1
const val EARTH_ID = 2
const val MOON_ID = 3

class MainActivity : AppCompatActivity() {

    private val sharedPreferences by lazy { getSharedPreferences(SETTINGS_SHARED_PREFERENCE, Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }

        when(themeId()) {
            MARS_ID -> {
                R.style.MarsTheme
                this.setTheme(MARS_ID)
            }
            EARTH_ID -> {
                R.style.EarthTheme
                this.setTheme(EARTH_ID)
            }
            MOON_ID -> {
                R.style.MoonTheme
                this.setTheme(MOON_ID)
            }
            else -> R.style.Theme_Material
        }
    }

    fun themeId(): Int = sharedPreferences.getInt(THEME_RES_ID,R.style.MarsTheme)
}