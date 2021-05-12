package com.example.yeelightapp.lamps

data class SetCommandClass(
    val id: Int,
    val method: String,
    val params: List<Any>
)