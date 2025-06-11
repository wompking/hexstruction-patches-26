package org.agent.hexstruction;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import net.minecraft.core.Registry;
import org.agent.hexstruction.patterns.*;

public final class HexStructionPatterns {
    public static void Init() {
        Register("save_structure", "dqeqdwdqeqd", HexDir.WEST, new OpSaveStructure());
        Register("load_structure", "aeqeawaeqea", HexDir.EAST, new OpLoadStructure());
        Register("mirror_left_right", "aeqeawaeqeaaewq", HexDir.EAST, new OpMirrorLeftRight());
        Register("mirror_front_back", "aeqeawaeqeaqqwe", HexDir.EAST, new OpMirrorFrontBack());
        Register("rotate_clockwise", "aeqeawaeqeaaede", HexDir.EAST, new OpRotateClockwise());
        Register("rotate_counterclockwise", "aeqeawaeqeaqqaq", HexDir.EAST, new OpRotateCounterClockwise());
        Register("display_structure", "aeqeawaeqeaqed", HexDir.EAST, new OpDisplayStructure());
        Register("bounding_box", "aeqeawaeqeaqqeqaqeq", HexDir.EAST, new OpGetBoundingBox());
        Register("transformations", "aeqeawaeqeaaee", HexDir.EAST, new OpGetTransformations());
    }

    private static void Register(String name, String signature, HexDir startDir, Action action) {
        Registry.register(HexActions.REGISTRY, Hexstruction.GetID(name), new ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), action));
    }
}
