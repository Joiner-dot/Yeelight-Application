package com.example.yeelightapp.data.api.enums.modetype

class TypeOfMode(private val command: String, private val params: String, private val nextLine:String) {

    override fun toString(): String {
        return "{\"id\":1, \"method\":\"$command\",\"params\":$params}$nextLine"
    }
}
