package com.codepalace.controllerProyecto2

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class WebSocketListener : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        webSocket.send("Hola papi")
        Log.e("burak", "conectao")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        fun outPut("recibido: $text")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        outPut("Closing: $code / $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        outPut("Error: "+ t.message)
    }

    fun outPut(text: String) {
        Log.e("websocket")
    }

    companion object{
        private const val NORMAL_CLOSURE_STATUS = 1000
    }
}