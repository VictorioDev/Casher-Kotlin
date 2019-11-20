package com.victorio.casher.network

import com.victorio.casher.entity.User
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface CasherService {


    @POST("login")
    fun login(@Body body: LoginRequestBody): Call<ResponseBody>

    @GET("")
    fun testApi(): Call<ResponseBody>


    @POST("users")
    fun register(@Body user: User): Call<ResponseBody>

    @GET("summary/{user_id}")
    fun getMovimentations(@Path("user_id") userId: String): Call<ResponseBody>


}