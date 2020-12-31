package com.github.poluka.kControlLibrary.actions.service.mototrs

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.actions.move.MoveToPoint
import com.github.poluka.kControlLibrary.dsl.Program
import com.github.poluka.kControlLibrary.enity.TypeOfMovement
import com.github.poluka.kControlLibrary.enity.position.Position

private const val command = "SERVICE"
@ExecutedOnTheRobot
class KMotors(private val kMotorStatus: KMotorStatus): Command {
    override fun run(): String = "$command;${kMotorStatus.name}"
}

fun Program.motorOn() = doWithCommand(KMotors(KMotorStatus.ON))
fun Program.motorOff() = doWithCommand(KMotors(KMotorStatus.OFF))