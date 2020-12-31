package com.github.art241111.tcpClient.connection

/**
 * ENUM class that describes
 * the possible connection status.
 *
 * @author Artem Gerasimov.
 */
enum class Status {
    DISCONNECTED,
    CONNECTING,
    COMPLETED,
    ERROR
}
