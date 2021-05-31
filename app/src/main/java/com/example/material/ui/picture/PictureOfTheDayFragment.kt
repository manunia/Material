package com.example.material.ui.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.example.material.MainActivity
import com.example.material.R
import com.example.material.ui.api.ApiActivity
import com.example.material.ui.apibottom.ApiBottomActivity
import com.example.material.ui.settings.SettingsFragment
import com.example.material.ui.picture.responceData.PODServerResponseData
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_main_start.*
import java.time.LocalDate

class PictureOfTheDayFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProviders.of(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_start, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_earth -> {
                    viewModel.getData(targetDate)
                        .observe(this@PictureOfTheDayFragment, Observer<PictureOfTheDayData> { renderData(it) })
                    true
                }
                R.id.bottom_view_mars -> {
                    viewModel.getData(targetDate.minusDays(1))
                        .observe(this@PictureOfTheDayFragment, Observer<PictureOfTheDayData> { renderData(it) })
                    true
                }
                R.id.bottom_view_weather -> {
                    viewModel.getData(targetDate.minusDays(2))
                        .observe(this@PictureOfTheDayFragment, Observer<PictureOfTheDayData> { renderData(it) })
                    true
                }
                else -> {
                    viewModel.getData(targetDate)
                        .observe(this@PictureOfTheDayFragment, Observer<PictureOfTheDayData> { renderData(it) })
                    true
                }
            }
        }
        bottom_navigation_view.selectedItemId = R.id.bottom_view_earth

        bottom_navigation_view.setOnNavigationItemReselectedListener { item ->
            when(item.itemId) {
                R.id.bottom_view_earth -> {

                }
                R.id.bottom_view_mars -> {

                }
                R.id.bottom_view_weather -> {

                }
            }
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }
        loadImage(url)



        setBottomAppBar(view)
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                serverResponseData = data.serverResponseData
                url = serverResponseData!!.url
                if (url.isNullOrEmpty()) {
                    toast("Empty link")
                } else {
                    loadImage(url)
                }

                val explanation: String? = serverResponseData!!.explanation
                if (explanation.isNullOrEmpty()) {
                    bottom_sheet_description.text = "Empty description"
                } else {
                    bottom_sheet_description.text = explanation
                }
                val title: String? = serverResponseData!!.title
                if (title.isNullOrEmpty()) {
                    bottom_sheet_description_header.text = "No title"
                } else {
                    bottom_sheet_description_header.text = title
                }
            }
            is PictureOfTheDayData.Loading -> {

            }
            is PictureOfTheDayData.Error -> {
                toast(data.error.message)
            }
        }
    }

    private fun loadImage(url: String?) {
        image_view.load(url) {
            lifecycle(this@PictureOfTheDayFragment)
            error(R.drawable.ic_load_error_vector)
            placeholder(R.drawable.ic_no_photo_vector)
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                toast("onSlide")
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_DRAGGING -> toast("STATE_DRAGGING")
                    BottomSheetBehavior.STATE_COLLAPSED -> toast("STATE_COLLAPSED")
                    BottomSheetBehavior.STATE_EXPANDED -> toast("STATE_EXPANDED")
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> toast("STATE_HALF_EXPANDED")
                    BottomSheetBehavior.STATE_HIDDEN -> toast("STATE_HIDDEN")
                    BottomSheetBehavior.STATE_SETTLING -> toast("STATE_SETTLING")
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> activity?.let { startActivity(Intent(it, ApiBottomActivity::class.java)) }
            R.id.app_bar_search -> toast("Search")
            R.id.app_bar_settings ->
                activity?.supportFragmentManager?.beginTransaction()?.add(
                    R.id.container, SettingsFragment.newInstance()
                )?.addToBackStack(null)?.commit()
            android.R.id.home -> {
                activity?.let {
                    BottomNavDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
            R.id.app_bar_api -> activity?.let { startActivity(Intent(it, ApiActivity::class.java)) }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
        fab.setOnClickListener {
            if (isMain) {
                isMain = false
                bottom_app_bar.navigationIcon = null
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                bottom_app_bar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }


    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true

        private var serverResponseData: PODServerResponseData? = null
        private var url: String? = null

        private var targetDate: LocalDate = LocalDate.now()
    }

}