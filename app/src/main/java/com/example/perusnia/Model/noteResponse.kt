package com.example.perusnia.Model


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class noteResponse(
    @SerializedName("data")
    val `data`: ArrayList<DataXXXX>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?
) : Parcelable