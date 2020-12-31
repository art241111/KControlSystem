package com.github.poluka.kControlLibrary.actions.move

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.actions.delay.Delay
import com.github.poluka.kControlLibrary.dsl.Program
import com.github.poluka.kControlLibrary.enity.TypeOfMovement
import com.github.poluka.kControlLibrary.enity.position.Position

private const val MOVE_TO_POINT = "MOVETO"

@ExecutedOnTheRobot
data class MoveToPoint(private val typeOfMovement: TypeOfMovement = TypeOfMovement.LMOVE,
                           private val position: Position): Command {
    override fun run() = "$MOVE_TO_POINT;$typeOfMovement;$position"
}

fun Program.moveToPoint(
        typeOfMovement: TypeOfMovement = TypeOfMovement.LMOVE,
        position: Position)
          = doWithCommand(MoveToPoint(typeOfMovement, position))