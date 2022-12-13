package com.example.perusnia.Model


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookRate_Response(
    @SerializedName("data")
    val `data`: ArrayList<DataXX>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?
) : Parcelable