package com.example.material.ui.picture

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.material.R
import com.example.material.ui.recycler.RecyclerActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_nav_layout.*

class BottomNavDrawerFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_nav_layout,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navigation_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_one -> activity?.let {
                    startActivity(Intent(
                        it,
                        RecyclerActivity::class.java
                    )
                    )
                }
                R.id.navigation_two -> Toast.makeText(context, "2", Toast.LENGTH_SHORT).show()
            }
            dismiss()
            true
        }
    }

}