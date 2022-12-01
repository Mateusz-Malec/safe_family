package com.example.safefamilyapp

import com.example.safefamilyapp.models.Login
import com.example.safefamilyapp.models.Register
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @Headers("Content-Type: application/json")
    @POST("/api/Registration")
     fun registerUser(@Body register: Register): Call<Register>

    @FormUrlEncoded
    @POST("api/Login")
     fun loginUser(
        @Field("Login") login: String,
        @Field("Password") password: String,
    ): Call<Login>

    @POST("/api/Login")
    fun loginUser2(
        @Query("Login") login: String,
        @Query("Password") password: String,
    ): Call<Login>

}