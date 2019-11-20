package com.victorio.casher.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.victorio.casher.Utils.Logger
import com.victorio.casher.data.LoginListener
import com.victorio.casher.data.Resource
import com.victorio.casher.data.State
import com.victorio.casher.entity.User
import kotlinx.coroutines.Deferred
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkDataSourceImpl : NetworkDataSource{




    var casherService: CasherService


    init {
        var retrofit = Retrofit.Builder().baseUrl("http://casher-api.herokuapp.com/api/").
            addConverterFactory(GsonConverterFactory.create()).
            build()
        casherService = retrofit.create(CasherService::class.java)
    }




    override fun testApi(loginListener: LoginListener) {
        var call = casherService.testApi()
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Log.i("CasherApp", "Failed ${t.message}")

                loginListener.onError(t.message)
            }

            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                if(response.isSuccessful){

                response?.body()?.string()?.let { loginListener.onSucess(it)
                    Log.i("CasherApp", "Sucess ${response?.body()!!.string()}, ${response.code()}")}
            }else{

                Logger.log("É nulo")
                Logger.log(response?.code().toString())
            }


            }
        })
    }

    override fun login(email: String, password: String, loginListener: LoginListener) {

        var call = casherService.login(LoginRequestBody(email, password))
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Log.i("CasherApp", "Failed ${t.message}")

                loginListener.onError(t.message)
            }

            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {

                if(response.body() != null){
                    response?.body()?.string()?.let { loginListener.onSucess(it)}
                }else{

                    Logger.log("${call.request().url()} - ${response.code()} - ${response}")

                }


            }
        })
    }



}