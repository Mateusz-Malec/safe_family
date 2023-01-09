package com.example.safefamilyapp

import android.content.Intent
import android.util.Log
import com.example.safefamilyapp.models.*
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {

    var responseBody: Any? = null
    var listOfLocation: ArrayList<LatLng>? = null

    fun registerUser(userData: Register, onResult: (Any?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(ApiInterface::class.java)

        //val retIn = ServiceBuilder.buildService().create(ApiInterface::class.java)

        retrofit.registerUser(userData).enqueue(
            object : Callback<Any> {
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    onResult(null)
                    Log.e("Error", t.message!!)
                }

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                    //Log.d("registerUser", response.code().toString() )

                    if (response.code() == 201) {
                        Log.d("Registration success!", response.toString())

                    } else {
                        Log.e("Registration failed!",
                            response.code().toString() + " " + response.message())
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

/*     fun loginUser(login: Login, onResult: (Login?) -> Unit){
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
    }*/

    fun loginUser2(login: Login, onResult: (AccessToken?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(ApiInterface::class.java)
        retrofit.loginUser(login.Login, login.Password).enqueue(
            object : Callback<AccessToken> {
                override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                    onResult(null)
                    Log.e("Error", t.message!!)
                }

                override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {
                    responseBody = response.body()

                    onResult(response.body())
                    if (response.code() == 200) {
                        Log.d("Login success!", response.body().toString())

                        response.body()?.let { Log.d("Login success!", it.Token) }
                    } else {
                        Log.e("Login failed!", response.code().toString())
                    }
                }
            }
        )
    }

    fun refreshToken(token: String, refreshToken: String, onResult: (AccessToken?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(ApiInterface::class.java)
        retrofit.refreshLogin(token, refreshToken).enqueue(
            object : Callback<AccessToken> {
                override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                    onResult(null)
                    Log.e("Error", t.message!!)
                }

                override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {

                    if (response.code() == 200) {
                        Log.d("Login refresh!", response.body().toString())
                        Log.i("ZWROTKA", response.toString())

                    } else {
                        Log.e("Refresh failed!",
                            response.code().toString() + " " + response.message())
                        Log.e("Failed refresh!----response", response.body().toString())
                        Log.e("zwrotka err", response.toString())
                    }
                }
            }
        )
    }


    fun displayProfile(token: String, onResult: (UserProfile?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(ApiInterface::class.java)

        retrofit.displayProfile(token).enqueue(
            object : Callback<UserProfile> {
                override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                    onResult(null)
                    Log.e("Error", t.message!!)
                }

                override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {

                    if (response.code() == 200) {
                        Log.d("Your profile data", response.body().toString())
                        onResult(response.body())
                    } else {
                        Log.e("Display failed!",
                            response.code().toString() + " " + response.message())
                    }
                }
            }
        )
    }

    fun addGuard(token: String, addGuard: Guard, onResult: (Any?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(ApiInterface::class.java)

        retrofit.addGuard(token, addGuard).enqueue(
            object : Callback<Any> {
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    onResult(null)
                    Log.e("Error", t.message!!)
                }

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    val addedGuard = response.body()
                    if (response.code() == (200 or 201)) {
                        Log.d("Registration guard success!", response.toString())
                        onResult(addedGuard)
                    } else {
                        Log.e("Registration Guard failed!",
                            response.code().toString() + " " + response.message())
                    }
                }
            }
        )
    }

    fun getGuards(token: String, onResult: (Array<GuardView>?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(ApiInterface::class.java)

        retrofit.getGuards(token).enqueue(
            object : Callback<Array<GuardView>> {
                override fun onFailure(call: Call<Array<GuardView>>, t: Throwable) {
                    onResult(null)
                    Log.e("Error", t.message!!)
                }

                override fun onResponse(call: Call<Array<GuardView>>, response: Response<Array<GuardView>>) {

                    if (response.code() == 200) {
                        Log.d("Your guards", response.body().toString())
                        onResult(response.body())
                    } else {
                        Log.e("Get guards failed!",
                            response.code().toString() + " " + response.toString())
                    }
                }
            }
        )
    }


    fun addDevice(token: String, device: Device, onResult: (Any?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(ApiInterface::class.java)
        retrofit.addDevice(token, device).enqueue(
            object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onResult(null)
                    Log.e("Error", t.message!!)
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.code() == 200) {
                        Log.d("Add device success!", response.toString())
                        onResult(response)
                    } else {
                        Log.e("Device connect failed!",
                            response.code().toString() + " " + response.message())
                    }
                }
            }
        )
    }

    fun addDeviceGuard(token: String, device: Device, onResult: (Any?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(ApiInterface::class.java)
        retrofit.addDeviceGuard(token, device).enqueue(
            object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onResult(null)
                    Log.e("Error", t.message!!)
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.code() == 200) {
                        Log.d("Add device success!", response.toString())
                        onResult(response)
                    } else {
                        Log.e("Device connect failed!",
                            response.code().toString() + " " + response.message())
                    }
                }
            }
        )
    }

    fun getAll(token: String, onResult: (Array<Device>?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(ApiInterface::class.java)

        retrofit.getAllLocation(token).enqueue(
            object : Callback<Array<Device>> {
                override fun onFailure(call: Call<Array<Device>>, t: Throwable) {
                    onResult(null)
                    Log.e("Error", t.message!!)
                }

                override fun onResponse(call: Call<Array<Device>>, response: Response<Array<Device>>) {

                    if (response.code() == 200) {
                        Log.d("Your devices", response.body().toString())
                        onResult(response.body())

                    } else {
                        Log.e("Get all devices failed!",
                            response.code().toString() + " " + response.toString())
                    }
                }
            }
        )
    }

    fun sendLocation(token: String,device: Device, onResult: (Any?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(ApiInterface::class.java)

        retrofit.sendLocation(token, device).enqueue(
            object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onResult(null)
                    Log.e("Error", t.message!!)
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {

                    if (response.code() == 200) {
                        Log.d("Your locations", response.body().toString())
                        onResult(response)
                    } else {
                        Log.e("Location sending failed!",
                            response.code().toString() + " " + response.toString())
                    }
                }
            }
        )
    }

    fun resetPassword(mail: String, onResult: (Any?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(ApiInterface::class.java)

        retrofit.resetPassword(mail).enqueue(
            object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onResult(null)
                    Log.e("Error", t.message!!)
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    onResult(response)
                    //Log.d("registerUser", response.code().toString() )

                    if (response.code() == 200) {
                        Log.d("Check your mail", response.toString())

                    } else if (response.code()==400) {
                        Log.d("E-mail nie istnieje", response.toString())
                    }
                    else {
                        Log.e("Registration failed!",
                            response.code().toString() + " " + response.message())
                    }
                }
            }
        )
    }
}