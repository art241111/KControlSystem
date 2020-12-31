package com.github.art241111.tcpClient.connection

import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketTimeoutException
import kotlin.properties.Delegates

/**
 * Create connection class.
 * @author Artem Gerasimov.
 */
class Connection {
    var socket = Socket()
    // Connect status.
    var status: Status by Delegates.observable(Status.DISCONNECTED) { _, _, newValue ->
        onStatusChanged?.invoke(newValue)
    }

    private var onStatusChanged: ((Status) -> Unit)? = null

    fun setStatusObserver(observer: ((Status) -> Unit)) {
        onStatusChanged = observer
//        onStatusChanged?.invoke(status)
    }

    /**
     * Disconnect from server.
     */
    fun disconnect(){
        if(status == Status.COMPLETED){
            socket.close()
            status = Status.DISCONNECTED

            socket = Socket()
            onStatusChanged = null
        }
    }

    /**
     * Connect to server by TCP/IP.
     * If the connection did not occur within 2 seconds,
     * the error status is displayed.
     */
    fun connect(address: String, port: Int) = connectToTheServer(address, port)


    private fun connectToTheServer(address: String, port: Int) {
        if(status != Status.CONNECTING && status != Status.COMPLETED){
            try {
                // Set connecting status
                status = Status.CONNECTING

                // Try to connect
                socket.connect(InetSocketAddress(address, port), 2000)

                // If the connection is successful, we notify you about it
                status = Status.COMPLETED
            } catch (e: SocketTimeoutException){
                status = Status.ERROR
                socket = Socket()
            }
        }
    }
}
