package com.omar.mvvm.Models.Response.SearchPhotos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//Retrofit Serialized names vs Parcelize it's more intuitive but more boilerplate code
@Parcelize
data class UnsplashPhoto(
    val id: String,
    val description: String?,
    val urls: PhotoUrls,
    val user: PhotoUser
) : Parcelable {
    @Parcelize
    data class PhotoUrls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String
    ) : Parcelable

    @Parcelize
    data class PhotoUser(
        val id: String,
        val username: String,
        val name: String,
    ) : Parcelable {
        val attrUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium_referral"
    }
}
