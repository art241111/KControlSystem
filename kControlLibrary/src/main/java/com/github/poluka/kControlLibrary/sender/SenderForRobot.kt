package com.github.poluka.kControlLibrary.sender

import com.github.art241111.tcpClient.writer.SafeSender
import com.github.art241111.tcpClient.writer.Sender
import com.github.poluka.kControlLibrary.handlers.programStatusHandler.ProgramStatusHandler
import com.github.poluka.kControlLibrary.handlers.programStatusHandler.ProgramStatusUpdate
import kotlinx.coroutines.*
import java.util.*

class SenderForRobot(private val sender: Sender): Sender, SafeSender,ProgramStatusUpdate {
    val programStatusHandler = ProgramStatusHandler(this)

    private var programStatus = ProgramStatus(ProgramStatusEnum.READY_TO_SEND)
    private val sendQueue: Queue<String> = LinkedList()
    private var isWriting = false

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    /**
     * Add message to queue.
     * @param text - the text that will be sent to the server.
     */
    override fun send(text: String) {
        sender.send(text)
    }

    override fun safeSend(text: String) {
        sendQueue.add(text)
    }

    /**
     * Handle queue.
     */
    private fun startSend(){
        scope.launch {
            while (isWriting){
                if(sendQueue.isNotEmpty() && programStatus.status == ProgramStatusEnum.READY_TO_SEND){
                    programStatus.status = ProgramStatusEnum.PROGRAM_IS_RUNNING
                    val text = sendQueue.poll()
                    if(!text.isNullOrEmpty()){
                        if (text.contains("Delay")){
                            delay(text.substringAfter(":").toLong())
                        } else{
                            send(text)
                        }
                    }
                    delay(5L)
                }
            }
        }
    }

    /**
     * Sending the final command and closing Writer.
     */
    override fun stopSending() {
        isWriting = false
        sendQueue.clear()
    }

    override fun startSending() {
        programStatus.status = ProgramStatusEnum.READY_TO_SEND
        isWriting = true
        sendQueue.clear()
        startSend()
    }

    override fun whenProgramStatusUpdate(statusUpdate: ProgramStatus) {
        programStatus = statusUpdate
    }
}