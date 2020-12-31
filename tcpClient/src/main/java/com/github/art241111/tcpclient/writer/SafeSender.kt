package com.github.art241111.tcpClient.writer

interface SafeSender {
    fun safeSend(text: String)
    fun stopSending()
    fun startSending()
}