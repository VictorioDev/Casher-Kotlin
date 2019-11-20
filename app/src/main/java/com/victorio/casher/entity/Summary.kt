package com.victorio.casher.entity

data class Summary(var balance: String, var positive_balance: Boolean, var entries: Int, var last_entries: ArrayList<Movimentation>)