package org.agent.hexstruction;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import dev.architectury.registry.registries.Registrar;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import org.agent.hexstruction.patterns.OpTestPattern;

public final class HexStructionPatterns {
    public static void Init() {
        Register("test_pattern", "eeed", HexDir.EAST, new OpTestPattern());
    }

    private static void Register(String name, String signature, HexDir startDir, Action action) {
        Registry.register(HexActions.REGISTRY, Hexstruction.GetID(name), new ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), action));
    }
}
