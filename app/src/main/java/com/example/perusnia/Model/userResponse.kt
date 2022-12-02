package com.example.perusnia.Model


import com.google.gson.annotations.SerializedName

data class userResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("Status")
    val status: Int?
)