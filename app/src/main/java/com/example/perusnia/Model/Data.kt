package com.example.perusnia.Model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    @SerializedName("alamat")
    val alamat: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("foto")
    val foto: String?,
    @SerializedName("id_level")
    val idLevel: String?,
    @SerializedName("id_users")
    val idUsers: String?,
    @SerializedName("jenis_kelamin")
    val jenisKelamin: String?,
    @SerializedName("kota")
    val kota: String?,
    @SerializedName("nama_belakang")
    val namaBelakang: String?,
    @SerializedName("nama_depan")
    val namaDepan: String?,
    @SerializedName("negara")
    val negara: String?,
    @SerializedName("no_telp")
    val noTelp: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("tgl_lahir")
    val tglLahir: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("username")
    val username: String?
):Parcelable