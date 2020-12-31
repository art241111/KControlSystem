package com.github.poluka.kControlLibrary.actions.delay

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.dsl.Program

/**
 *
 */
private const val command = "SERVICE;WSIGNAL"
@ExecutedOnTheRobot
class WaitingSignal(private val signal: Int): Command {
    override fun run(): String = "$command;$signal"
}

fun Program.waitSignal(signal: Int) = doWithCommand(WaitingSignal(signal))