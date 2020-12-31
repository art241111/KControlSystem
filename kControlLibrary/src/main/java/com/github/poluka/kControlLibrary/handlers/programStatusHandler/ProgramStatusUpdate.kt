package com.github.poluka.kControlLibrary.handlers.programStatusHandler

import com.github.poluka.kControlLibrary.sender.ProgramStatus

interface ProgramStatusUpdate {
    fun whenProgramStatusUpdate(statusUpdate: ProgramStatus)
}