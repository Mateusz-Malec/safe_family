package com.example.safefamilyapp.models

import java.time.LocalDateTime

data class RefreshToken (
    val Token: String,
    val CreatedDate: String,
    val ValidTo: String
        )