@file:Suppress("DEPRECATION")

package me.xap3y.dscbridge.managers

import org.bukkit.Bukkit
import org.bukkit.ChatColor

class TextManager {

    private val prefix = "&8[&6DSCBridge&8] &r"

    fun debugMessage(msg: String): String =
        coloredMessage("&8[&6D&8] &r${msg.replace("<prefix>", prefix)}")

    fun coloredMessage(msg: String): String =
        ChatColor.translateAlternateColorCodes('&', msg.replace("<prefix>", prefix))

    fun console(msg: String) =
        Bukkit.getConsoleSender().sendMessage(coloredMessage(msg.replace("<prefix>", prefix)))
}