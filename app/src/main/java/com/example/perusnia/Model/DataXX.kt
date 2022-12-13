package com.example.perusnia.Model


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataXX(
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("foto")
    val foto: String?,
    @SerializedName("id_rate_book")
    val idRateBook: String?,
    @SerializedName("nama_lengkap")
    val namaLengkap: String?,
    @SerializedName("rate_score")
    val rateScore: String?
) : Parcelable