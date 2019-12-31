package com.victorio.casher.network

import com.google.gson.annotations.SerializedName
import com.victorio.casher.entity.Movimentation

data class MovimentationsResponse(
    var entries : Int = 0,
    @SerializedName("hits")
    var movimentations: List<Movimentation>?
)
