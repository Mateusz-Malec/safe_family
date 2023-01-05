package com.example.safefamilyapp.models

data class AccessToken (
    val Token: String,
    val status: Int,
    val Role: String,
    val RefreshToken: RefreshToken
)