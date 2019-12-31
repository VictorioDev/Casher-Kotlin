package com.victorio.casher.entity

data class Movimentation(
    var id : Int = 0,
    var type : String,
    var name: String,
    var value: Double,
    var date : String = "",
    var user_id: Int = 82,
    var category_id: Int = 1
)