package me.xap3y.dscbridge.managers

import me.xap3y.dscbridge.Main

class ConfigManager(private val plugin: Main) {

    fun loadConfig() {
        if (!plugin.isConfigFileInit()) return
        if (!plugin.configFile.exists()) {
            plugin.saveResource(plugin.configFile.name, false)
        }
        plugin.reloadConfig()
    }

    fun getMessage(value: String, default: String): String =
        plugin.config.getString(value) ?: default

    fun getInt(value: String, default: Int): Int =
        plugin.config.getInt(value) ?: default

    fun getBoolean(value: String, default: Boolean): Boolean =
        plugin.config.getBoolean(value) ?: default
}