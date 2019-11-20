package com.victorio.casher.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.victorio.casher.R
import com.victorio.casher.data.LoginViewModel
import com.victorio.casher.entity.User
import com.victorio.casher.network.LoginRequestBody
import com.victorio.casher.network.NetworkDataSourceImpl
import kotlinx.android.synthetic.main.activity_login_activity.*
import kotlinx.coroutines.*
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel : LoginViewModel
    private var currentContext = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_activity)





        viewModel = ViewModelProviders.of(this)[LoginViewModel::class.java]




        /*viewModel.getState().observe(this, Observer {
            com.victorio.casher.Utils.Logger.log(it.state.name)
            when(it.state){
                State.LOADING -> progressBar.visibility = View.VISIBLE
                State.SUCESS -> {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                State.ERROR -> {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })*/

        loginButton.setOnClickListener{
            UIUtil.hideKeyboard(this)
            CoroutineScope(Dispatchers.Main).launch {
                loadingContainer.visibility = View.VISIBLE
                var cService = NetworkDataSourceImpl().casherService
                var requestBody = LoginRequestBody(emailEditText.text.toString(), passwordEditText.text.toString())
                var call = withContext(Dispatchers.IO){
                    cService.login(requestBody).execute()
                }


                if(call.isSuccessful){
                    var resp = call.body()?.string()
                    //Toast.makeText(currentContext,"Sucess $resp", Toast.LENGTH_SHORT).show()
                    var gson = Gson()
                    var user : User?
                    resp.let {
                        user = gson.fromJson<User>(resp, User::class.java)
                        com.victorio.casher.Utils.Logger.log("UserID: ${user?.user_id}")
                        com.victorio.casher.Utils.Logger.log(resp)
                        var intent = Intent(currentContext, BalanceActivity::class.java)
                        intent.putExtra("user_id", user?.user_id)
                        startActivity(intent)
                    }


                }else {
                    loadingContainer.visibility = View.INVISIBLE
                    Toast.makeText(currentContext, "Error ${call.raw()}", Toast.LENGTH_SHORT).show()
                }
            }
            //viewModel.testApi()
            //viewModel.login()
        }

        sigInTextView.setOnClickListener{
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }




    }
}
