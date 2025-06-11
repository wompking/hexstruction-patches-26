package org.agent.hexstruction.mixin;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(StructureTemplate.Palette.class)
public abstract interface PaletteAccessor {
    @Invoker("<init>")
    public static StructureTemplate.Palette create(List<StructureTemplate.StructureBlockInfo> list) { return null; };
}
