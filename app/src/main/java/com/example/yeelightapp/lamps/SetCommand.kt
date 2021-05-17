package com.example.yeelightapp.lamps

data class SetCommand(
    val id: Int,
    val method: String,
    val params: List<Any>
)