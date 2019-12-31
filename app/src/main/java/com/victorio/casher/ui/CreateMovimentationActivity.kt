package com.victorio.casher.ui

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.DatePicker
import android.widget.SimpleAdapter
import androidx.core.animation.addListener
import com.google.android.material.animation.AnimatorSetCompat
import com.victorio.casher.R
import com.victorio.casher.entity.Movimentation
import com.victorio.casher.network.CasherService
import kotlinx.android.synthetic.main.activity_create_movimentation.*
import kotlinx.android.synthetic.main.movimentation_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import java.util.*
import kotlin.coroutines.CoroutineContext

class CreateMovimentationActivity : AppCompatActivity() {


    lateinit var datePickerListener : DatePickerDialog.OnDateSetListener
    lateinit var casherService: CasherService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_movimentation)


        var categoryAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        categoryAdapter.addAll("Transport", "Food")
        mov_category.adapter = categoryAdapter

        var typeAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        typeAdapter.addAll("Debit", "Credit")
        mov_type.adapter = typeAdapter

        datePickerListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val date = "$dayOfMonth/${month + 1}/$year"
                mov_date.text = date
            }

        mov_date.setOnClickListener {
            showDialog()
        }

        casherService = CasherService.getInstance()

        btn_salvar.setOnClickListener {
            saveMovimentation()
        }



    }






    private fun showDialog(){
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this,datePickerListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun saveMovimentation(){
        val movimentation = Movimentation(
            name = mov_name.text.toString(),
            value = mov_value.text.toString().toDouble(),
            type = if(mov_type.selectedItem == "Credit") "C" else "D"
        )

        CoroutineScope(Dispatchers.Default).launch {
            var response : Response<ResponseBody>? = null
            try {
                response = withContext(Dispatchers.IO){
                    casherService.createMovimentation(movimentation)
                }

                response.let {
                    if(it.isSuccessful){
                        Log.d("VAZP", "Sucesso ${response?.body().toString()}")
                        finish()
                    }else {
                        Log.d("VAZP", "Deu ruim, status code ${response?.code()}")
                    }
                }
            }catch (ex: Exception){
                Log.d("VAZP", "Erro: ${ex.message} - ${response?.raw()?.request()?.url()}")
            }



        }







    }
}
