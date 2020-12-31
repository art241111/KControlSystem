package com.github.art241111.tcpClient.connection

/**
 * Interface that should implement the
 * class that will connect to the server.
 * @author Artem Gerasimov.
 */
interface ConnectInt {
    suspend fun connect(address: String, port: Int)
    fun disconnect()

//    fun getConnectStatus(): Status by Dele
}
