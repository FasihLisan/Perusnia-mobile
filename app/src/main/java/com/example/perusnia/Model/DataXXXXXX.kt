package com.example.perusnia.Model


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataXXXXXX(
    @SerializedName("amount")
    val amount: String?,
    @SerializedName("approval_code")
    val approvalCode: String?,
    @SerializedName("bank")
    val bank: String?,
    @SerializedName("bill_key")
    val billKey: String?,
    @SerializedName("biller_code")
    val billerCode: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("fraud_status")
    val fraudStatus: String?,
    @SerializedName("gross_amount")
    val grossAmount: String?,
    @SerializedName("id_book")
    val idBook: String?,
    @SerializedName("id_detail_transaction")
    val idDetailTransaction: String?,
    @SerializedName("id_payment_amount")
    val idPaymentAmount: String?,
    @SerializedName("id_transaction")
    val idTransaction: String?,
    @SerializedName("id_users")
    val idUsers: String?,
    @SerializedName("id_va_numbers")
    val idVaNumbers: String?,
    @SerializedName("merchant_id")
    val merchantId: String?,
    @SerializedName("order_id")
    val orderId: String?,
    @SerializedName("paid_at")
    val paidAt: String?,
    @SerializedName("payment_type")
    val paymentType: String?,
    @SerializedName("settlement_time")
    val settlementTime: String?,
    @SerializedName("signature_key")
    val signatureKey: String?,
    @SerializedName("status_code")
    val statusCode: String?,
    @SerializedName("status_message")
    val statusMessage: String?,
    @SerializedName("transaction_id")
    val transactionId: String?,
    @SerializedName("transaction_status")
    val transactionStatus: String?,
    @SerializedName("transaction_time")
    val transactionTime: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("va_number")
    val vaNumber: String?
) : Parcelable