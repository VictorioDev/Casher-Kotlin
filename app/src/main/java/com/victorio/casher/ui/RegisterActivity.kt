package com.victorio.casher.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.victorio.casher.R
import com.victorio.casher.entity.User
import com.victorio.casher.network.NetworkDataSourceImpl
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.*

class RegisterActivity : AppCompatActivity() {


    var currentContext = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        logInTextView.setOnClickListener {
            finish()
        }

        var networkDataSourceImpl = NetworkDataSourceImpl()
        registerButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                loadingContainer.visibility = View.VISIBLE
                var casherService = networkDataSourceImpl.casherService
                var user = User(name = fullNameEditText.text.toString(), email = emailEditText.text.toString(), password = passwordEditText.text.toString())
                var call = withContext(Dispatchers.IO) {
                    casherService.register(user).execute()
                }

                loadingContainer.visibility = View.INVISIBLE
                if(call.isSuccessful) {
                    Snackbar.make(it, "User created!", Snackbar.LENGTH_LONG).show()
                    delay(1500)
                    finish()
                    //Toast.makeText(currentContext, call.body()?.string(), Toast.LENGTH_SHORT).show()
                }else {
                    Snackbar.make(it, "Something went wrong!", Snackbar.LENGTH_LONG).show()
                    Toast.makeText(currentContext, call.errorBody().toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

