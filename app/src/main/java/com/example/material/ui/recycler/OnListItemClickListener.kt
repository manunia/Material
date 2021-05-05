package com.example.material.ui.recycler

import com.example.material.ui.picture.responceData.PODServerResponseData

interface OnListItemClickListener {
    fun onItemClick(data: PODServerResponseData)
}