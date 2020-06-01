package com.example.lab_8.network

import android.text.Html
import com.squareup.moshi.Json

data class News(
    val id: String,
    // used to map img_src from the JSON to imgSrcUrl in our class
    @Json(name = "img_src") val imgSrcUrl: String,
    val type: String,
    val price: Double
)
