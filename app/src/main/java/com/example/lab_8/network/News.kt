package com.example.lab_8.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import retrofit2.http.Url

@Parcelize
data class News(
   val created_at: String?,
   val title: String?,
   val author: String?,
    val points: String?,
    @Json(name = "url") val urlNews: String?
) : Parcelable
