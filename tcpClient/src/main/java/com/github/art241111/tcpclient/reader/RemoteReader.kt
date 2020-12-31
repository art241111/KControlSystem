package com.github.art241111.tcpClient.reader

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.github.art241111.tcpClient.handlers.HandlerImp
import java.io.BufferedReader
import java.lang.Exception
import java.net.Socket

/**
 * This class creates a Reader that will listen to the
 * incoming stream and process the data that the server sends.
 * @author Artem Gerasimov.
 */
class RemoteReader: RemoteReaderImp {
    // List of handlers that listen for input data
    private val handlers: MutableList<HandlerImp> = mutableListOf()

    // A variable that displays whether our reader is working.
    private var isReading = false

    // Reader for receiving and reading incoming data from the server
    private lateinit var reader: BufferedReader

    /**
     * This method is required in order to add a handler
     * that will listen for incoming data.
     * @param handlers - list of handlers that will listen
     * for the incoming stream
     */
    override fun addHandlers(handlers: List<HandlerImp>){
        this.handlers.addAll(handlers)
    }

    /**
     * Stop reading track.
     */
    override fun destroyReader(){
        isReading = false
        handlers.clear()

        if(::reader.isInitialized && isReading){
            GlobalScope.launch(Dispatchers.IO) {
                reader.close()
            }
        }
    }

    /**
     * Start reading from InputStream
     * @param socket - the connection that you want to listen to.
     */
    override fun createReader(socket: Socket) {
        reader = socket.getInputStream().bufferedReader()
        isReading = true

        GlobalScope.launch {
            startTrackingInputString(reader)
        }
    }
        private fun startTrackingInputString(reader: BufferedReader){
            while (isReading){
                try {
                    if(reader.ready()){
                        val line = reader.readLine()
                        handlers.forEach {
                            it.handle(line)
                        }
                    }
                } catch (e: NullPointerException) {
//                     Log.e("reader", "No elements come", e)
                } catch (e: Exception){
//                    Log.e("reader", "Unknown error", e)
                }
            }
        }
}
