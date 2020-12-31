package com.github.poluka.kControlLibrary.actions.gripper

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.delay.Delay
import com.github.poluka.kControlLibrary.actions.move.MoveByCoordinate
import com.github.poluka.kControlLibrary.dsl.Program
import com.github.poluka.kControlLibrary.enity.Coordinate

data class RotateGripper(val angleOfRotation: Double): Command{
    override fun run(): String =
        MoveByCoordinate(Coordinate.T, angleOfRotation).run()
}

fun Program.rotateGripper(angleOfRotation: Double) = doWithCommand(RotateGripper(angleOfRotation))