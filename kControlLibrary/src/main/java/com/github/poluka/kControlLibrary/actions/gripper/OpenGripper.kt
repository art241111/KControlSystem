package com.github.poluka.kControlLibrary.actions.gripper

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.actions.delay.Delay
import com.github.poluka.kControlLibrary.dsl.Program

private const val OPEN_GRIPPER = "SERVICE;CLAMP;ON"

@ExecutedOnTheRobot
class OpenGripper: Command {
    override fun run(): String = OPEN_GRIPPER
}

fun Program.openGripper() = doWithCommand(OpenGripper())