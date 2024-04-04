@file:Suppress("DEPRECATION")

package me.xap3y.dscbridge.listeners

import me.xap3y.dscbridge.Main
import me.xap3y.dscbridge.ws.WsResObj
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class PlayerChatListener(private val plugin: Main): Listener {

    @EventHandler
    fun onPlayerChat(e: AsyncPlayerChatEvent) {
        if (!plugin.isWebSocketServerInit()) return

        val player = e.player
        val message = e.message

        plugin.webSocketServer.broadcastMessage(
            WsResObj()
                .addProperty("type", "event_playerChat")
                .addProperty("player", player.name)
                .addProperty("message", message)
                .build()
        )
    }
}