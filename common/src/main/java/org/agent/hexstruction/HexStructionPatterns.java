package org.agent.hexstruction;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import net.minecraft.core.Registry;
import org.agent.hexstruction.patterns.OpLoadStructure;
import org.agent.hexstruction.patterns.OpSaveStructure;

// todo: make actual patterns
public final class HexStructionPatterns {
    public static void Init() {
        Register("save_structure", "eeed", HexDir.EAST, new OpSaveStructure());
        Register("load_structure", "eqe", HexDir.EAST, new OpLoadStructure());
    }

    private static void Register(String name, String signature, HexDir startDir, Action action) {
        Registry.register(HexActions.REGISTRY, Hexstruction.GetID(name), new ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), action));
    }
}
