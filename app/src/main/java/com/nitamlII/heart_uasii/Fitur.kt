package com.nitamlII.heart_uasii

import android.os.Parcelable
import kotlinx.parcelize.Parcelize



@Parcelize
data class Fitur(
    val name: String,
    val description: String,
    val photo: Int,
) : Parcelable