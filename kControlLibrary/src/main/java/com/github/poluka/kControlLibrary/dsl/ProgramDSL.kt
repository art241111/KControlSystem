package com.github.poluka.kControlLibrary.dsl

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.actions.delay.Delay
import com.github.poluka.kControlLibrary.actions.delay.WaitingSignal
import com.github.poluka.kControlLibrary.actions.gripper.CloseGripper
import com.github.poluka.kControlLibrary.actions.gripper.OpenGripper
import com.github.poluka.kControlLibrary.actions.gripper.RotateGripper
import com.github.poluka.kControlLibrary.actions.move.DepartPoint
import com.github.poluka.kControlLibrary.actions.move.MoveByCoordinate
import com.github.poluka.kControlLibrary.actions.move.MoveC
import com.github.poluka.kControlLibrary.actions.move.MoveToPoint
import com.github.poluka.kControlLibrary.actions.service.signal.Signal
import com.github.poluka.kControlLibrary.enity.Coordinate
import com.github.poluka.kControlLibrary.enity.TypeOfMovement
import com.github.poluka.kControlLibrary.enity.position.Position

/**
 * DSL метод, за счет которого можно создавать свои программы.
 * @param dslCommands - команды, которые нужно добавить в программу.
 */

fun kProgram(dslCommands: Program.() -> Unit)= Program(dslCommands)

fun Program.runWithAction(action: (commands: Command) -> Unit){
    with(this){
        funOnCommand = action
        apply(dslCommands)
    }
}

/**
 * Класс, который позволяет добавлять команды в програму.
 * @author artem241120@gmail.com (Artem Gerasimov)
 */
@ExecutedOnTheRobot
class Program(val dslCommands: Program.() -> Unit) {
    internal lateinit var funOnCommand: (commands: Command) -> Unit

    internal fun doWithCommand(command: Command){
        if(this::funOnCommand.isInitialized){
            funOnCommand(command)
        }
    }

    operator fun invoke(command: Command) = add(command)

    /**
     * Если требуется добавить не реализованную команду или
     * сохраненную в переменную, то используйте add.
     * @param command - команда, которую требуется добавить в Program.
     */
    fun add(@ExecutedOnTheRobot command: Command) = doWithCommand(command)
//    fun add(@ExecutedOnTheRobot command: Program) = addCommands(command.getAll())
}
