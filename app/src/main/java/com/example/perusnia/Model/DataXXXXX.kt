package com.example.perusnia.Model


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataXXXXX(
    @SerializedName("id_users")
    val idUsers: String?,
    @SerializedName("total_harga")
    val totalHarga: String?,
    @SerializedName("total_item")
    val totalItem: String?
) : Parcelable