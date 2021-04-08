package com.omar.mvvm.Models.Response.SearchPhotos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class SearchPhotosResponse(
    val total: Int,
    val total_pages: Int,
    val results: List<UnsplashPhoto>,
) : Parcelable
