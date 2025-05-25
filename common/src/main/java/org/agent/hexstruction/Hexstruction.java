package org.agent.hexstruction;

import at.petrak.hexcasting.common.lib.hex.HexIotaTypes;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.resources.ResourceLocation;

public final class Hexstruction {
    public static final String MOD_ID = "hexstruction";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static void init() {
        // Write common init code here.
        LOGGER.info("Initializing HexStruction");
        HexStructionPatterns.Init();
        Registry.register(HexIotaTypes.REGISTRY, GetID("structure"), StructureIota.TYPE);
    }

    public static ResourceLocation GetID(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
