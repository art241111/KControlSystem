package com.github.poluka.kControlLibrary.actions

import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot

@ExecutedOnTheRobot
interface Command {
    // Get command
    fun run():String
}