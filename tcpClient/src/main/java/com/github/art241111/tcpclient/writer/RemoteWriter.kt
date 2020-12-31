package com.github.art241111.tcpClient.writer

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.PrintStream
import java.net.Socket
import java.util.*

/**
 * This class creates a Writer that allows
 * you to send text strings to the server.
 * @author Artem Gerasimov.
 */
class RemoteWriter: RemoteWriterImp, Sender, SafeSender {
    // A variable that displays whether our Writer is working.
    private var isWriting = false

    // Writer, which allows you to send data to the server
    private lateinit var writer: PrintStream

    private val sendQueue: Queue<String> = LinkedList()
    private var delay = 0L

    private var isSafeSendStart = false

    /**
     * Add message to queue.
     * @param text - the text that will be sent to the server.
     */
    override fun send(text: String) {
        writer.print(text)
        writer.flush()
    }

    override fun safeSend(text: String) {
        sendQueue.add(text)

        if(!isSafeSendStart) startSend()
    }

    override fun stopSending() {
        isWriting = false
        sendQueue.clear()
    }

    override fun startSending() {
        isWriting = true
        sendQueue.clear()

    }

    /**
     * Creating a writer that will send data to the server.
     * @param socket - - the connection that you want to listen to.
     */
    override fun createWriter(socket: Socket, delay: Long) {
        this.delay = delay
        writer = PrintStream(socket.getOutputStream())

        startSending()
    }

    /**
     * Sending the final command and closing Writer.
     */
    override fun destroyWriter(stopCommand: String) {
        if(::writer.isInitialized && isWriting){
            if(stopCommand != ""){
                send(stopCommand)
                Thread.sleep(50L)
            }
            writer.close()
        }

        stopSending()
    }

    /**
     * Handle queue.
     */
    private fun startSend(){
        isSafeSendStart = true
        GlobalScope.launch {
            while (isWriting){
                if(sendQueue.isNotEmpty()){
                    val text = sendQueue.poll()
                    if(!text.isNullOrEmpty()){
                        send(text)
                    }
                }

                delay(delay)
            }
        }
    }
}
