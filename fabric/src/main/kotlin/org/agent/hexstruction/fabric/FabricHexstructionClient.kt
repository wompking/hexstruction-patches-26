package org.agent.hexstruction.fabric

import org.agent.hexstruction.HexstructionClient
import net.fabricmc.api.ClientModInitializer

object FabricHexstructionClient : ClientModInitializer {
    override fun onInitializeClient() {
        HexstructionClient.init()
    }
}
