package com.example.material.ui.chips

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import com.example.material.R
import kotlinx.android.synthetic.main.settings_fragment.*

const val SETTINGS_SHARED_PREFERENCE = "SETTINGS_SHARED_PREFERENCE"
const val THEME_NAME_SHARED_PREFERENCE = "THEME_NAME_SHARED_PREFERENCE"
const val THEME_RES_ID = "THEME_RES_ID"
const val MARS = "MARS"
const val EARTH = "EARTH"
const val MOON = "MOON"

class SettingsFragment : Fragment() {

    private lateinit var theme: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val themeId: Int = requireActivity().getSharedPreferences(SETTINGS_SHARED_PREFERENCE, Context.MODE_PRIVATE)
            .getInt(THEME_RES_ID, R.style.MarsTheme)
        val inflaterNew: LayoutInflater = LayoutInflater.from(ContextThemeWrapper(context, themeId))
        return inflaterNew.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSharedPref()

        mars_theme_chip.setOnClickListener {

            if (theme != MARS) {
                requireContext().apply {
                    setTheme(R.style.MarsTheme)
                    saveTheme(MARS, R.style.MarsTheme)
                    Toast.makeText(this, MARS, Toast.LENGTH_SHORT)
                    recreate(requireActivity())
                }
            }
        }
        earth_theme_chip.setOnClickListener {

            if (theme != EARTH) {
                requireContext().apply {
                    setTheme(R.style.EarthTheme)
                    saveTheme(EARTH, R.style.EarthTheme)
                    Toast.makeText(this, EARTH, Toast.LENGTH_SHORT)
                    recreate(requireActivity())
                }
            }
        }
        moon_theme_chip.setOnClickListener {
            if (theme != MOON) {
                requireContext().apply {
                    setTheme(R.style.MoonTheme)
                    saveTheme(MOON, R.style.MoonTheme)
                    Toast.makeText(this, MOON, Toast.LENGTH_SHORT)
                    recreate(requireActivity())
                }
            }
        }

    }

    private fun saveTheme(themeName: String, theme_id: Int) {
        this.theme = themeName
        activity?.let {
            with(it.getSharedPreferences(SETTINGS_SHARED_PREFERENCE, Context.MODE_PRIVATE).edit()) {
                putString(THEME_NAME_SHARED_PREFERENCE, themeName).commit()
                putInt(THEME_RES_ID, theme_id).commit()
            }
        }
    }

    private fun setSharedPref() {
        activity?.let {
            theme =
                it.getSharedPreferences(SETTINGS_SHARED_PREFERENCE, Context.MODE_PRIVATE)
                .getString(THEME_NAME_SHARED_PREFERENCE, EARTH).toString()
            when (theme) {
                MARS -> {
                    mars_theme_chip.isChecked = true
                }
                EARTH -> {
                    earth_theme_chip.isChecked = true
                }
                MOON -> {
                    moon_theme_chip.isChecked = true
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}