package me.xap3y.dscbridge.listeners

import me.xap3y.dscbridge.Main
import me.xap3y.dscbridge.ws.WsResObj
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener(private val plugin: Main): Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {

        if (!plugin.isWebSocketServerInit()) return

        val player = event.player

        plugin.webSocketServer.broadcastMessage(
            WsResObj()
                .addProperty("type", "event_playerJoin")
                .addProperty("player", player.name)
                .build()
        )
    }
}