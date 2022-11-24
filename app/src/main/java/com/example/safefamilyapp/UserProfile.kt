package com.example.safefamilyapp

data class UserProfile(
    val Name: String? = null,
    val SurName: String,
    val Email: String,
    val Login: String,
    val Password: String,
    val PhoneNumber: String
)