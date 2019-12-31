package com.victorio.casher.network

import com.victorio.casher.entity.Movimentation
import com.victorio.casher.entity.User
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface CasherService {


    @POST("login")
    fun login(@Body body: LoginRequestBody): Call<ResponseBody>

   @POST("movimentations")
   suspend fun createMovimentation(@Body movimentation: Movimentation) : Response<ResponseBody>


    @POST("users")
    fun register(@Body user: User): Call<ResponseBody>

    @GET("summary/{user_id}")
    fun getSummary(@Path("user_id") userId: String): Call<ResponseBody>


    @GET("movimentations/{user_id}")
    suspend fun getMovimentations(@Path("user_id") userId: String): Response<ResponseBody>


    companion object {
        const val URL = "http://casher-api.herokuapp.com/api/"
        private var rInstance : CasherService? = null

        fun getInstance() : CasherService  {
            return rInstance ?: Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(CasherService::class.java)
                .also { rInstance = it } }



    }


}