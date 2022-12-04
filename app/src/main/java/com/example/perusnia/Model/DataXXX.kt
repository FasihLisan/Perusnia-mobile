package com.example.perusnia.Model


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp

@Parcelize
data class DataXXX(
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id_book")
    val idBook: String?,
    @SerializedName("id_rate_book")
    val idRateBook: String?,
    @SerializedName("id_users")
    val idUsers: String?,
    @SerializedName("rate_score")
    val rateScore: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
) : Parcelable