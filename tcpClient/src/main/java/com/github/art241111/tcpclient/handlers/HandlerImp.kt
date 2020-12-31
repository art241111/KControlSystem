package com.github.art241111.tcpClient.handlers

/**
 * Interface for creating data handlers
 *  that come to the server.
 */
interface HandlerImp {
    /**
     * The handle method is called
     * when a string arrives from the server.
     * @param text - the string arrives that came from the server
     */
    fun handle(text: String)
}