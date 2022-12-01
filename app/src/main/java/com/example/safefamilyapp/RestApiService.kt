package com.example.safefamilyapp

import android.util.Log
import com.example.safefamilyapp.models.Login
import com.example.safefamilyapp.models.Register
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {

     fun registerUser(userData: Register, onResult: (Register?) -> Unit){
        val retrofit = ServiceBuilder.buildService(ApiInterface::class.java)

        //val retIn = ServiceBuilder.buildService().create(ApiInterface::class.java)

         retrofit.registerUser(userData).enqueue(
             object : Callback<Register> {
                 override fun onFailure(call: Call<Register>, t: Throwable) {
                     onResult(null)
                     Log.e("Error", t.message!!)
                 }

                 override fun onResponse(call: Call<Register>, response: Response<Register>) {
                     val addedUser = response.body()
                     onResult(addedUser)
                     //Log.d("registerUser", response.code().toString() )

                     if (response.code() == 201) {
                         Log.d("Registration success!", response.toString())

                     } else {
                         Log.e("Registration failed!", response.code().toString() + " " + addedUser.toString())
                     }
                 }
             }
         )

         /*val jsonObjectString = jsonObject.toString()

         // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
         val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

         CoroutineScope(Dispatchers.IO).launch {
             // Do the POST request and get response
             //val response = service.createEmployee(requestBody)

             val response = retrofit.registerUser(requestBody)
             withContext(Dispatchers.Main) {
                 if (response.isSuccessful) {

                     // Convert raw JSON to pretty JSON using GSON library
                     val gson = GsonBuilder().setPrettyPrinting().create()
                     val prettyJson = gson.toJson(
                         JsonParser.parseString(
                             response.body() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                         )
                     )

                     Log.d("Pretty Printed JSON :", prettyJson)

                 } else {

                     Log.e("RETROFIT_ERROR", response.code().toString())

                 }
             }
         }*/
    }

     fun loginUser(login: Login, onResult: (Login?) -> Unit){
        val retrofit = ServiceBuilder.buildService(ApiInterface::class.java)
        retrofit.loginUser(login.Login, login.Password).enqueue(
            object : Callback<Login> {
                override fun onFailure(call: Call<Login>, t: Throwable) {
                    onResult(null)
                    Log.e("Error", t.message!!)
                }

                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    val loggedUser = response.body()
                    onResult(loggedUser)
                    if (response.code() == 200) {
                        Log.d("Login success!", response.message())

                    }
                    else{
                        Log.e("Login failed!", response.code().toString())
                    }
                }
            }
        )
    }

    fun loginUser2(login: Login, onResult: (Login?) -> Unit){
        val retrofit = ServiceBuilder.buildService(ApiInterface::class.java)
        retrofit.loginUser2(login.Login, login.Password).enqueue(
            object : Callback<Login> {
                override fun onFailure(call: Call<Login>, t: Throwable) {
                    onResult(null)
                    Log.e("Error", t.message!!)
                }

                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    val loggedUser = response.body()
                    onResult(loggedUser)
                    if (response.code() == 200) {
                        Log.d("Login success!", response.message())
                    }
                    else{
                        Log.e("Login failed!", response.code().toString())
                    }
                }
            }
        )
    }
}