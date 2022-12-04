package com.example.perusnia.Model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class bookResponse(
    @SerializedName("data")
    val `data`: ArrayList<DataX>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?
) : Parcelable