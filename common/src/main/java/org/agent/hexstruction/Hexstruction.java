package org.agent.hexstruction;

import at.petrak.hexcasting.common.lib.hex.HexIotaTypes;
import net.minecraft.core.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.resources.ResourceLocation;

// todo: configure forge gradle
public final class Hexstruction {
    public static final String MOD_ID = "hexstruction";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static void init() {
        LOGGER.info("Initializing HexStruction");
        HexStructionPatterns.Init();
        Registry.register(HexIotaTypes.REGISTRY, GetID("structure"), StructureIota.TYPE);
    }

    public static ResourceLocation GetID(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
