package org.agent.hexstruction.fabric

import org.agent.hexstruction.Hexstruction
import net.fabricmc.api.ModInitializer

object FabricHexstruction : ModInitializer {
    override fun onInitialize() {
        Hexstruction.init()
    }
}
