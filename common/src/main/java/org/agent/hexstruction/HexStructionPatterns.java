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
// todo: Transformations
// todo: Using as a template
// valid mirrors: NONE, LEFT_RIGHT, FRONT_BACK
// valid rotations: NONE, CLOCKWISE_90, CLOCKWISE_180, COUNTERCLOCKWISE_90
public final class HexStructionPatterns {
    public static void Init() {
        Register("save_structure", "dqeqdwdqeqd", HexDir.WEST, new OpSaveStructure());
        Register("load_structure", "aeqeawaeqea", HexDir.EAST, new OpLoadStructure());
    }

    private static void Register(String name, String signature, HexDir startDir, Action action) {
        Registry.register(HexActions.REGISTRY, Hexstruction.GetID(name), new ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), action));
    }
}
