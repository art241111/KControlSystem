package com.github.art241111.kcontrolsystem

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.art241111.tcpClient.connection.Status
import com.github.poluka.kControlLibrary.KRobot
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.actions.move.moveByCoordinate
import com.github.poluka.kControlLibrary.actions.move.moveToPoint
import com.github.poluka.kControlLibrary.dsl.kProgram
import com.github.poluka.kControlLibrary.enity.Coordinate
import com.github.poluka.kControlLibrary.enity.TypeOfMovement

class MainActivity : AppCompatActivity() {
    private val address = "192.168.31.63"
    private val port = 49152

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    private val robot = KRobot()

    private val listener  = MySensorEventListener(robot)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }


    fun connect(view: View) {
        robot.setConnectRobotStatusObserver {
            Log.d("status_observe", it.toString())
        }

        robot.runWhenConnect(
                kProgram {
                    moveToPoint(TypeOfMovement.LMOVE, robot.homePosition)
                }
        )

        robot.connect(address, port)

        listener.robot = robot

        if (sensor != null) {
            sensorManager.registerListener(
                listener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        } else {
            Log.e("sensor","Sensor not detected")
        }
    }

    fun disconnect(view: View) {
        robot.disconnect()

        if (sensor != null) {
            sensorManager.unregisterListener(listener, sensor)
        } else {
            Log.e("sensor","Sensor not detected")
        }
    }

    private class MySensorEventListener(var robot: KRobot): SensorEventListener {

        private var lastTime = java.util.Calendar.getInstance().timeInMillis

        override fun onSensorChanged(event: SensorEvent?) {
            val timeNow = java.util.Calendar.getInstance().timeInMillis
            Log.d("time_send", "Last time: $lastTime, now: $timeNow")
            if(timeNow - lastTime > 200){
                lastTime = timeNow
                if (event != null) {
                    robot.run(kProgram {
                        moveByCoordinate(Coordinate.X, -1 * event.values[0].toDouble())
                        moveByCoordinate(Coordinate.Y, -1 * event.values[1].toDouble())
                    })

                    Log.d("coordinate_pos",
                            "x: ${event.values[0].toDouble()} Y: ${event.values[1].toDouble()}")
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            Log.d("sensor","$sensor: $accuracy")
        }
    }


}