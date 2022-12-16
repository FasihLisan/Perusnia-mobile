package com.example.perusnia.Model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataX(
    @SerializedName("author")
    val author: String?,
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("cover")
    val cover: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("file_buku")
    val fileBuku: String?,
    @SerializedName("halaman")
    val halaman: String?,
    @SerializedName("foto")
    val foto: String?,
    @SerializedName("harga")
    val harga: String?,
    @SerializedName("id_book")
    val idBook: String?,
    @SerializedName("id_rate_book")
    val idRateBook: String?,
    @SerializedName("id_users")
    val idUsers: String?,
    @SerializedName("judul")
    val judul: String?,
    @SerializedName("kode_buku")
    val kodeBuku: String?,
    @SerializedName("payment_id")
    val paymentId: String?,
    @SerializedName("publication_date")
    val publicationDate: String?,
    @SerializedName("publisher_name")
    val publisherName: String?,
    @SerializedName("rate_book")
    val rateBook: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("username")
    val username: String?
) : Parcelable