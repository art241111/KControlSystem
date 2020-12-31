package com.github.poluka.kControlLibrary.handlers

import com.github.art241111.tcpClient.handlers.HandlerImp
import com.github.poluka.kControlLibrary.enity.position.Position
import com.github.poluka.kControlLibrary.enity.position.positionArrayFromString
import kotlin.properties.Delegates

class PositionHandler: HandlerImp {
    // Connect status.
    var position: Position by Delegates.observable(Position()) { _, oldValue, newValue ->
        if(oldValue != newValue){
            onStatusChanged?.invoke(newValue)
        }
    }

    private var onStatusChanged: ((Position) -> Unit)? = null

    fun setPositionObserver(observer: ((Position) -> Unit)) {
        onStatusChanged = observer
    }

    private var positionStr = ""

    override fun handle(text: String) {
        if(text.substringBefore(";").trim() == "POINT"){
            val newPosition = positionParsing(text)
            if(newPosition != null){
                position = newPosition
            }
        }
    }

    private fun positionParsing(position: String): Position? =
        if(isNewValue(position)){
            Position().positionArrayFromString(positionStr)
        } else{
             null
        }

    private fun isNewValue(position: String): Boolean =
        if(positionStr == "" || positionStr != position){
            positionStr = position
            true
        } else false
}