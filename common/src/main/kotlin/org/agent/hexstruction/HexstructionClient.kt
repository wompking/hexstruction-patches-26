package org.agent.hexstruction

import org.agent.hexstruction.config.HexstructionConfig
import org.agent.hexstruction.config.HexstructionConfig.GlobalConfig
import me.shedaniel.autoconfig.AutoConfig
import net.minecraft.client.gui.screens.Screen

object HexstructionClient {
    fun init() {
        HexstructionConfig.initClient()
    }

    fun getConfigScreen(parent: Screen): Screen {
        return AutoConfig.getConfigScreen(GlobalConfig::class.java, parent).get()
    }
}
