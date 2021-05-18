package com.example.yeelightapp.data.api.enums

import com.example.yeelightapp.data.api.enums.modetype.TypeOfMode

enum class Modes(val command: String) {
    Night(
        TypeOfMode(
            Commands.SetScene.command,
            ParamsForModes.Night.parameters,
            Tools.NextLine.tool
        ).toString()
    ),
    Work(
        TypeOfMode(
            Commands.SetScene.command,
            ParamsForModes.Work.parameters,
            Tools.NextLine.tool
        ).toString()
    ),
    Party(
        TypeOfMode(
            Commands.SetScene.command,
            ParamsForModes.Party.parameters,
            Tools.NextLine.tool
        ).toString()
    ),
    Romantic(
        TypeOfMode(
            Commands.SetScene.command,
            ParamsForModes.Romantic.parameters,
            Tools.NextLine.tool
        ).toString()
    )
}
