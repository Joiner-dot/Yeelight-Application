package com.example.yeelightapp.data.api.enums

enum class Modes(val command: String) {
    Night("{\"id\":1, \"method\":\"set_scene\",\"params\":[\"cf\",0,0,\"5000,1,16755200,1,5000,1,16744960,1\"]}\r\n"),
    Work("{\"id\":1, \"method\":\"set_scene\",\"params\":[\"cf\",0,0,\"5000,1,16777215,60,15000,1,16760480,40\"]}\r\n"),
    Party("{\"id\":1, \"method\":\"set_scene\",\"params\":[\"cf\",0,0,\"2000,1,16711680,80,2000,1,16755200,80,2000,1,65280,80,2000,1,65535,80,2000,1,16711935,80,2000,1,255,80\"]}\r\n"),
    Romantic("{\"id\":1, \"method\":\"set_scene\",\"params\":[\"cf\",0,0,\"2000,1,16711870,60,800,1,11141375,40\"]}\r\n")
}