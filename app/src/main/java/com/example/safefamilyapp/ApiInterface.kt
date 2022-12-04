package com.example.safefamilyapp

import com.example.safefamilyapp.models.Login
import com.example.safefamilyapp.models.Register
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @Headers("Content-Type: application/json")
    @POST("/api/Registration")
    fun registerUser(@Body register: Register): Call<Register>

/*    @FormUrlEncoded
    @POST("api/Login")
     fun loginUser(
        @Field("Login") login: String,
        @Field("Password") password: String,
    ): Call<Login>*/

    @POST("/api/Login")
    fun loginUser(
        @Query("Login") login: String,
        @Query("Password") password: String,
    ): Call<Any>

    @POST("/api/Login/refresh")
    fun refreshLogin(@Body refreshToken: Any?): Call<Any>

}