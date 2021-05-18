package com.example.yeelightapp.data.api.enums

enum class Modes(val command: String) {
    Night("{\"id\":1, \"method\":\"${Commands.SetScene.command}\",\"params\":${ParamsForModes.Night.nameMode}}\r\n"),
    Work("{\"id\":1, \"method\":\"${Commands.SetScene.command}\",\"params\":${ParamsForModes.Work.nameMode}}\r\n"),
    Party("{\"id\":1, \"method\":\"${Commands.SetScene.command}\",\"params\":${ParamsForModes.Party.nameMode}}\r\n"),
    Romantic("{\"id\":1, \"method\":\"${Commands.SetScene.command}\",\"params\":${ParamsForModes.Romantic.nameMode}}\r\n")
}