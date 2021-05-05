package com.example.material.ui.recycler

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.example.material.R
import com.example.material.ui.picture.PictureOfTheDayData
import com.example.material.ui.picture.PictureOfTheDayFragment
import com.example.material.ui.picture.PictureOfTheDayViewModel
import com.example.material.ui.picture.responceData.PODServerResponseData
import kotlinx.android.synthetic.main.activity_recycler.*
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_main_start.*
import java.time.LocalDate

class RecyclerActivity : AppCompatActivity() {

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProviders.of(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_recycler)
        viewModel.getData(targetDate.minusDays(1))
            .observe(this@RecyclerActivity, Observer<PictureOfTheDayData> { renderData(it) })
        val data = listOf(
            serverResponseData,
            serverResponseData
            )

        recyclerView.adapter = RecyclerActivityAdapter(
            object : RecyclerActivityAdapter.OnListItemClickListener {
                override fun onItemClick(data: PODServerResponseData) {
                    Toast.makeText(this@RecyclerActivity, data.title, Toast.LENGTH_SHORT).show()
                }
            },
            data
        )

    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                serverResponseData = data.serverResponseData
                url = serverResponseData!!.url
                if (url.isNullOrEmpty()) {

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

            }
        }
    }

    private fun loadImage(url: String?) {
        image_view.load(url) {
            lifecycle(this@RecyclerActivity)
            error(R.drawable.ic_load_error_vector)
            placeholder(R.drawable.ic_no_photo_vector)
        }
    }

    companion object {

        private var serverResponseData: PODServerResponseData? = null
        private var url: String? = null
        private var targetDate: LocalDate = LocalDate.now()
    }
}