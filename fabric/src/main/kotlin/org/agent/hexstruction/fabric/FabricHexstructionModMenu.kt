package org.agent.hexstruction.fabric

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import org.agent.hexstruction.HexstructionClient

object FabricHexstructionModMenu : ModMenuApi {
    override fun getModConfigScreenFactory() = ConfigScreenFactory(HexstructionClient::getConfigScreen)
}
