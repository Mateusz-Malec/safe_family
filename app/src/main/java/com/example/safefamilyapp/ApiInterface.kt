package com.example.safefamilyapp

import com.example.safefamilyapp.models.*
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @Headers("Content-Type: application/json")
    @POST("/api/Registration")
    fun registerUser(@Body register: Register): Call<Any>

/*    @FormUrlEncoded
    @POST("api/Login")
     fun loginUser(
        @Field("Login") login: String,
        @Field("Password") password: String,
    ): Call<Login>*/

    @Headers("Content-Type: application/json")
    @POST("/api/Login")
    fun loginUser(
        @Query("Login") login: String,
        @Query("Password") password: String,
    ): Call<AccessToken>

    //@POST("/api/Login/refresh")
    //@Headers("Content-Type: text/plain")
    //@POST("/api/Login/refreshTest")
    //fun refreshLogin(@Body refreshToken: String): Call<Any>

    @POST("/api/Login/refresh")
    fun refreshLogin(@Query("Token") token: String,
                     @Query("RefreshToken") refreshToken: String): Call<AccessToken>

    //@GET("/api/User/getProfile")

    @GET("/api/User/getProfile")
    fun displayProfile(@Header("Authorization") token: String): Call<UserProfile>


    //@Headers("Content-Type: application/json")
    @PUT("/api/User/AddGuard")
    fun addGuard(@Header("Authorization") token: String,
                 @Body addGuard: Guard): Call<Any>

    @GET("/api/User/GetGuards")
    fun getGuards(@Header("Authorization") token: String): Call<Array<GuardView>>


    @PUT("/api/User/AddDevice")
    fun addDevice(@Header("Authorization") token: String,
                 @Body device: Device): Call<Any>
}