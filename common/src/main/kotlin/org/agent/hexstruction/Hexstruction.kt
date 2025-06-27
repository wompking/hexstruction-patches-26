package org.agent.hexstruction

import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.agent.hexstruction.config.HexstructionConfig
import org.agent.hexstruction.networking.HexstructionNetworking
import org.agent.hexstruction.registry.HexstructionActions
import org.agent.hexstruction.registry.HexstructionIotaTypes

object Hexstruction {
    const val MODID = "hexstruction"

    @JvmField
    val LOGGER: Logger = LogManager.getLogger(MODID)

    @JvmStatic
    fun id(path: String) = ResourceLocation(MODID, path)

    fun init() {
        HexstructionConfig.init()
        initRegistries(
            HexstructionActions,
            HexstructionIotaTypes
        )
        HexstructionNetworking.init()
    }
}
