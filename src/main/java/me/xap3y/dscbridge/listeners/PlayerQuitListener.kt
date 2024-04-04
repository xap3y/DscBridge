package me.xap3y.dscbridge.listeners

import me.xap3y.dscbridge.Main
import me.xap3y.dscbridge.ws.WsResObj
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener(private val plugin: Main): Listener {

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {

        if (!plugin.isWebSocketServerInit()) return

        val player = e.player

        plugin.webSocketServer.broadcastMessage(
            WsResObj()
                .addProperty("type", "event_playerQuit")
                .addProperty("player", player.name)
                .build()
        )
    }
}