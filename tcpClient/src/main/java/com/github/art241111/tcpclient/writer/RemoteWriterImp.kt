package com.github.art241111.tcpClient.writer

import java.net.Socket

/**
 * Interface that should implement the class
 * that will connect to send data to the server.
 * @author Artem Gerasimov.
 */
interface RemoteWriterImp {
    fun createWriter(socket: Socket, delay: Long = 0L)
    fun destroyWriter(stopCommand: String = "q")
}