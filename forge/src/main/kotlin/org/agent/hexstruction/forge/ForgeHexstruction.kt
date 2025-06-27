package org.agent.hexstruction.forge

import dev.architectury.platform.forge.EventBuses
import org.agent.hexstruction.Hexstruction
import net.minecraft.data.DataProvider
import net.minecraft.data.DataProvider.Factory
import net.minecraft.data.PackOutput
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(Hexstruction.MODID)
class HexstructionForge {
    init {
        MOD_BUS.apply {
            EventBuses.registerModEventBus(Hexstruction.MODID, this)
            addListener(ForgeHexstructionClient::init)
            addListener(::gatherData)
        }
        Hexstruction.init()
    }

    private fun gatherData(event: GatherDataEvent) {
        event.apply {
            // TODO: add datagen providers here
        }
    }
}

fun <T : DataProvider> GatherDataEvent.addProvider(run: Boolean, factory: (PackOutput) -> T) =
    generator.addProvider(run, Factory { factory(it) })
