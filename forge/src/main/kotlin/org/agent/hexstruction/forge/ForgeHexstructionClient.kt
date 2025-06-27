package org.agent.hexstruction.forge

import org.agent.hexstruction.HexstructionClient
import net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import thedarkcolour.kotlinforforge.forge.LOADING_CONTEXT

object ForgeHexstructionClient {
    fun init(event: FMLClientSetupEvent) {
        HexstructionClient.init()
        LOADING_CONTEXT.registerExtensionPoint(ConfigScreenFactory::class.java) {
            ConfigScreenFactory { _, parent -> HexstructionClient.getConfigScreen(parent) }
        }
    }
}
