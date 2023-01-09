package com.example.safefamilyapp.models

data class GuardView(
    val Name: String,
    val SurName: String,
    val Login: String,
    val LastActive: String,
    val Devices: ArrayList<DeviceView>
)
