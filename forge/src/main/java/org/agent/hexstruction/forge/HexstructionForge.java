package org.agent.hexstruction.forge;

import org.agent.hexstruction.Hexstruction;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Hexstruction.MOD_ID)
public final class HexstructionForge {
    public HexstructionForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(Hexstruction.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        Hexstruction.init();
    }
}
