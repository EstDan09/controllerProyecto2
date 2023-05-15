package com.codepalace.controllerProyecto2

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import java.io.BufferedReader
import java.io.PrintWriter
import java.net.Socket


class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var square: TextView
    private lateinit var botonsky: Button
    private lateinit var webSocketListener: okhttp3.WebSocketListener

    private val socket: Socket? = null
    private val input: BufferedReader? = null
    private val output: PrintWriter? = null

    private val SERVER_IP // IP de la máquina Linux
            : String? = null
    private val SERVER_PORT // Puerto en el que la máquina Linux está escuchando
            = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Keeps phone in light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        square = findViewById(R.id.tv_square)
        botonsky = findViewById(R.id.button2)

        setUpSensorStuff()
    }

    private fun setUpSensorStuff() {
        // Create the sensor manager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        // Specify the sensor you want to listen to
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // Checks for the sensor we have registered
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            //Log.d("Main", "onSensorChanged: sides ${event.values[0]} front/back ${event.values[1]} ")

            // Sides = Tilting phone left(10) and right(-10)
            val sides = event.values[0]

            // Up/Down = Tilting phone up(10), flat (0), upside-down(-10)
            val upDown = event.values[1]

            square.apply {
                rotationX = upDown * 3f
                rotationY = sides * 3f
                rotation = -sides
                translationX = sides * -10
                translationY = upDown * 10
            }

            // Changes the colour of the square if it's completely flat
            val color = if (upDown.toInt() == 0 && sides.toInt() == 0) Color.GREEN else Color.RED
            square.setBackgroundColor(color)

            if (upDown.toInt() < 0 && sides.toInt() < 3 && sides.toInt() > -3){
                square.text = "Moving Up"
            }

            if (upDown.toInt() > 0 && sides.toInt() < 3 && sides.toInt() > -3){
                square.text = "Moving Down"
            }

            if (sides.toInt() > 0 && upDown.toInt() < 3 && upDown.toInt() > -3){
                square.text = "Moving Left"
            }

            if (sides.toInt() < 0 && upDown.toInt() < 3 && upDown.toInt() > -3){
                square.text = "Moving Right"
            }
            //square.text = "up/down ${upDown.toInt()}\nleft/right ${sides.toInt()}"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }
}

