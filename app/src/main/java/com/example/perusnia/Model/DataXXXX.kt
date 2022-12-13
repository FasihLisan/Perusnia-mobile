package com.example.perusnia.Model


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataXXXX(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id_notes")
    val idNotes: String?,
    @SerializedName("id_users")
    val idUsers: String?,
    @SerializedName("isi")
    val isi: String?,
    @SerializedName("judul")
    val judul: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
) : Parcelable