package com.github.poluka.kControlLibrary.actions.gripper

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.actions.delay.Delay
import com.github.poluka.kControlLibrary.dsl.Program

private const val CLOSE_GRIPPER = "SERVICE;CLAMP;OFF"

@ExecutedOnTheRobot
 class CloseGripper: Command {
    override fun run(): String = CLOSE_GRIPPER
}

fun Program.closeGripper() = doWithCommand(CloseGripper())