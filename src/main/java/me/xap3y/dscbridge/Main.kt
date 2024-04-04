package me.xap3y.dscbridge

import me.xap3y.dscbridge.listeners.PlayerChatListener
import me.xap3y.dscbridge.listeners.PlayerJoinListener
import me.xap3y.dscbridge.listeners.PlayerQuitListener
import me.xap3y.dscbridge.managers.ConfigManager
import me.xap3y.dscbridge.managers.TextManager
import me.xap3y.dscbridge.ws.WebSocketServer
import org.bukkit.event.Listener
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.net.InetAddress
import java.net.InetSocketAddress

class Main : JavaPlugin() {

    private lateinit var configManager: ConfigManager
    lateinit var configFile: File

    lateinit var webSocketServer: WebSocketServer
    private lateinit var webSocketThread: Thread

    val textApi: TextManager by lazy { TextManager() }

    var serverAddress: InetSocketAddress = InetSocketAddress(InetAddress.getLocalHost(), 8080)

    override fun onEnable() {

        if (!dataFolder.exists())
            dataFolder.mkdir()

        configFile = File(dataFolder, "config.yml")

        configManager = ConfigManager(this)
        configManager.loadConfig()

        regiserListeners()

        val wsHost = configManager.getMessage("wsHost", "localhost")
        val wsPort = configManager.getInt("wsPort", 8080)

        textApi.console("<prefix>&fStarting WebSocket server on &a$wsHost:$wsPort")

        try {
            serverAddress = InetSocketAddress(wsHost, wsPort)

            webSocketThread = Thread {
                webSocketServer = WebSocketServer(this)
                webSocketServer.isReuseAddr = true
                webSocketServer.start()
            }
        } catch (e: Exception) {
            textApi.console("<prefix>&cFailed to start WebSocket server: ${e.message}")
        }


        server.scheduler.runTaskTimerAsynchronously(this, Runnable {
            try {
                if (!isWebSocketThreadInit()) return@Runnable
                if (!webSocketThread.isAlive) {
                    webSocketThread.start()
                }
            } catch (e: Exception) {
                // Ignore
            }
        }, 0L, 20L * 5L)

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    private fun regiserListeners() {
        val list: Set<Listener> = setOf(
            PlayerJoinListener(this),
            PlayerQuitListener(this),
            PlayerChatListener(this)
        )

        val pm: PluginManager = server.pluginManager

        list.forEach {
            pm.registerEvents(it, this)
        }
    }

    fun isConfigFileInit() = ::configFile.isInitialized
    fun isWebSocketServerInit() = ::webSocketServer.isInitialized
    fun isWebSocketThreadInit() = ::webSocketThread.isInitialized
}
