package com.example.perusnia.Model

data class LoginResponse(
    val error:Boolean,
    val message:String,
    val user: User
)
