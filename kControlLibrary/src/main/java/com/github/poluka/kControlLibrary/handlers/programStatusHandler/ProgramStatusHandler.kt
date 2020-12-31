package com.github.poluka.kControlLibrary.handlers.programStatusHandler

import com.github.art241111.tcpClient.handlers.HandlerImp
import com.github.poluka.kControlLibrary.sender.ProgramStatus
import com.github.poluka.kControlLibrary.sender.ProgramStatusEnum

class ProgramStatusHandler(private val statusUpdate: ProgramStatusUpdate): HandlerImp {
    override fun handle(text: String) {
        if(text.contains("PROGRAM")){
            if(text.contains("SUCCESSFULLY")){
                statusUpdate.whenProgramStatusUpdate(
                        ProgramStatus(status = ProgramStatusEnum.READY_TO_SEND)
                )
            } else if(text.contains("ERROR")){
                statusUpdate.whenProgramStatusUpdate(
                        ProgramStatus(status = ProgramStatusEnum.ERROR)
                        // TODO: add error text
                )
            }
        }
    }
}