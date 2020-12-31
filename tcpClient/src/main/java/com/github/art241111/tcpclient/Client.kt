package com.github.art241111.tcpClient

import com.github.art241111.tcpClient.connection.Connection
import com.github.art241111.tcpClient.connection.Status
import com.github.art241111.tcpClient.handlers.HandlerImp
import com.github.art241111.tcpClient.reader.RemoteReader
import com.github.art241111.tcpClient.writer.RemoteWriter
import com.github.art241111.tcpClient.writer.SafeSender
import com.github.art241111.tcpClient.writer.Sender
import kotlinx.coroutines.*
import java.net.Socket

/**
 * TCP client.
 * @author Artem Gerasimov.
 */
class Client(){
    private val connection = Connection()
    private val remoteReader = RemoteReader()
    private val remoteWriter = RemoteWriter()
    fun getSender(): Sender = remoteWriter
    fun getSafeSender(): SafeSender = remoteWriter


    /**
     * @return connect status
     */
    fun setStatusObserver(observer: ((Status) -> Unit)) = connection.setStatusObserver(observer)

    fun addHandlers(handlers: List<HandlerImp>) {
        remoteReader.addHandlers(handlers)
    }

    /**
     * Connect to TCP sever.
     * @param address - server ip port,
     * @param port - server port.
     */
    suspend fun connect(address: String,
                port: Int,
                senderDelay: Long = 0L){
        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.IO + job)// Add handlers to Reader

        // When the device connects to the server, it creates Reader and Writer
        withContext(scope.coroutineContext) {
            connection.connect(address, port)

            while (connection.status != Status.ERROR) {
                // When the device connects to the server, it creates Reader and Writer
                if (connection.socket.isConnected) {
                    startReadingAndWriting(socket = connection.socket, senderDelay)
                    break
                } else {
                    this.cancel()
                }
            }
        }
    }

    /**
     * Disconnect from TCP sever.
     */
    fun disconnect(){
        GlobalScope.launch {
            remoteReader.destroyReader()
            remoteWriter.destroyWriter()

            delay(50L)
            connection.disconnect()
        }
    }

    /**
     * Send text to the server.
     */
    fun send(text: String) {
        remoteWriter.send(text)
    }

    private fun startReadingAndWriting(socket: Socket, delay: Long) {
        remoteReader.createReader(socket)
        remoteWriter.createWriter(socket, delay)
    }
}