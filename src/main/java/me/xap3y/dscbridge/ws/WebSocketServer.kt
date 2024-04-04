package me.xap3y.dscbridge.ws

import com.google.gson.JsonObject
import me.xap3y.dscbridge.Main
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import java.lang.Exception
import java.util.concurrent.ConcurrentHashMap

class WebSocketServer(private val plugin: Main): org.java_websocket.server.WebSocketServer(plugin.serverAddress) {

    private val connectionAliveMap = ConcurrentHashMap<WebSocket, Boolean>()

    override fun onOpen(p0: WebSocket, p1: ClientHandshake?) {
        //plugin.textApi.console("<prefix>&f")
        connectionAliveMap[p0] = true
    }

    override fun onClose(p0: WebSocket?, p1: Int, p2: String?, p3: Boolean) {
        // Ignore
    }

    override fun onMessage(p0: WebSocket?, p1: String?) {
        // Ignore
    }

    override fun onError(p0: WebSocket?, p1: Exception?) {
        // Ignore
    }

    override fun onStart() {
        plugin.textApi.console("<prefix>&aWebSocketServer started on &e${address.hostName}:${address.port}")
    }

    fun broadcastMessage(obj: JsonObject) {
        connectionAliveMap.keys.forEach { conn ->
            if (connectionAliveMap[conn] != true) {
                return@forEach
            }
            if (conn.isOpen) {
                sendToClient(conn, obj)
            } else {
                connectionAliveMap[conn] = false
            }
        }
    }

    private fun sendToClient(conn: WebSocket, content: JsonObject) {
        //plugin.textApi.console("<prefix>&fSending message to &e${conn.remoteSocketAddress}")
        //plugin.textApi.console("<prefix>&fMessage: &e$content")
        try {
            conn.send(content.toString())
        } catch (ex: Exception) {
            // Ignore
        }
    }
}