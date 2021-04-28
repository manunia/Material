package com.example.material.ui.chips

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.material.R
import kotlinx.android.synthetic.main.settings_fragment.*




class SettingsFragment : Fragment() {

    private val NAME_SHARED_PREFERENCE = "LOGIN"
    private val APP_THEME = "APP_THEME"
    private val MARS = 0
    private val EARTH = 1
    private val MOON = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.setTheme(getAppTheme(R.style.MarsTheme))
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    private fun getAppTheme(themeId: Int): Int {
        return getThemeId(getSharedPrefs(themeId))
    }

    private fun getSharedPrefs(themeId: Int): Int {
        val sharedPref: SharedPreferences? = activity?.getSharedPreferences(
            NAME_SHARED_PREFERENCE, Context.MODE_PRIVATE
        )
        return sharedPref?.getInt(APP_THEME,themeId)!!
    }

    fun getThemeId(themeId: Int):Int {
        return when(themeId) {
            MARS -> R.style.MarsTheme
            MOON -> R.style.MoonTheme
            EARTH -> R.style.EarthTheme
            else -> R.style.MarsTheme
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mars_theme_chip.setOnClickListener {
            if (!it.isSelected) {
                saveTheme(R.style.MarsTheme)
                activity?.recreate()
                it.isSelected = true
            }
        }
        earth_theme_chip.setOnClickListener {
            if (!it.isSelected) {
                saveTheme(R.style.EarthTheme)
                activity?.recreate()
                it.isSelected = true
            }
        }
        moon_theme_chip.setOnClickListener {
            if (!it.isSelected) {
                saveTheme(R.style.MoonTheme)
                activity?.recreate()
                it.isSelected = true
            }
        }

    }

    private fun saveTheme(theme_id: Int) {
        val sharedPref: SharedPreferences? = activity?.getSharedPreferences(
            NAME_SHARED_PREFERENCE, Context.MODE_PRIVATE
        )
        val editor = sharedPref?.edit()
        editor?.putInt(APP_THEME, theme_id)
        editor?.apply()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}