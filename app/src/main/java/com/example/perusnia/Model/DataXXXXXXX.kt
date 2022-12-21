package com.example.perusnia.Model


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataXXXXXXX(
    @SerializedName("alamat")
    val alamat: String?,
    @SerializedName("approval_code")
    val approvalCode: String?,
    @SerializedName("author")
    val author: String?,
    @SerializedName("bill_key")
    val billKey: String?,
    @SerializedName("biller_code")
    val billerCode: String?,
    @SerializedName("cover")
    val cover: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("file_buku")
    val fileBuku: String?,
    @SerializedName("foto")
    val foto: String?,
    @SerializedName("fraud_status")
    val fraudStatus: String?,
    @SerializedName("gross_amount")
    val grossAmount: String?,
    @SerializedName("halaman")
    val halaman: String?,
    @SerializedName("harga")
    val harga: String?,
    @SerializedName("id_book")
    val idBook: String?,
    @SerializedName("id_detail_transaction")
    val idDetailTransaction: String?,
    @SerializedName("id_level")
    val idLevel: String?,
    @SerializedName("id_transaction")
    val idTransaction: String?,
    @SerializedName("id_users")
    val idUsers: String?,
    @SerializedName("jenis_kelamin")
    val jenisKelamin: String?,
    @SerializedName("judul")
    val judul: String?,
    @SerializedName("kode_buku")
    val kodeBuku: String?,
    @SerializedName("kota")
    val kota: String?,
    @SerializedName("merchant_id")
    val merchantId: String?,
    @SerializedName("nama_belakang")
    val namaBelakang: String?,
    @SerializedName("nama_depan")
    val namaDepan: String?,
    @SerializedName("negara")
    val negara: String?,
    @SerializedName("no_telp")
    val noTelp: String?,
    @SerializedName("order_id")
    val orderId: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("payment_id")
    val paymentId: String?,
    @SerializedName("payment_type")
    val paymentType: String?,
    @SerializedName("publication_date")
    val publicationDate: String?,
    @SerializedName("settlement_time")
    val settlementTime: String?,
    @SerializedName("signature_key")
    val signatureKey: String?,
    @SerializedName("status_code")
    val statusCode: String?,
    @SerializedName("status_message")
    val statusMessage: String?,
    @SerializedName("tgl_lahir")
    val tglLahir: String?,
    @SerializedName("transaction_id")
    val transactionId: String?,
    @SerializedName("transaction_status")
    val transactionStatus: String?,
    @SerializedName("transaction_time")
    val transactionTime: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("username")
    val username: String?
) : Parcelable