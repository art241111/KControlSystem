package com.github.poluka.kControlLibrary.actions.service.signal

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.actions.move.MoveC
import com.github.poluka.kControlLibrary.dsl.Program
import com.github.poluka.kControlLibrary.enity.position.Position

private const val command = "SERVICE;SIGNAL"
@ExecutedOnTheRobot
class Signal(private val signal: Int): Command {
    override fun run(): String = "$command;$signal"
}

fun Program.signalOn(signal: Int) = doWithCommand(Signal(signal))

fun Program.signalOff(signal: Int) = doWithCommand(Signal(-signal))