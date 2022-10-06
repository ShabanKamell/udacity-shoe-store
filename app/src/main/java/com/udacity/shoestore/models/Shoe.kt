package com.udacity.shoestore.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shoe(
    var name: String,
    var size: String,
    var company: String,
    var description: String,
    val images: List<String> = emptyList()
) : Parcelable {
    companion object {
        val empty: Shoe
            get() = Shoe(
                name = "",
                size = "",
                company = "",
                description = "",
                images = emptyList(),
            )
    }
}