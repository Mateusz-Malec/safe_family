package com.example.safefamilyapp.models

data class Register(
    val Login: String,
    val Password: String,
    val PasswordConfirm: String,
    val Name: String,
    val SurName: String,
    val Email: String,
    val PhoneNumber: String
)