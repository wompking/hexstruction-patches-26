package org.agent.hexstruction.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class HexstructionBlockTags {
    public static final TagKey<Block> STRUCTURE_SAVE_BANNED = TagKey.create(Registries.BLOCK, new ResourceLocation("hexstruction", "structure_save_banned"));
}
