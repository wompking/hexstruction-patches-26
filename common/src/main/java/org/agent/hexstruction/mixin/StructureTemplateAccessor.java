package org.agent.hexstruction.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Display;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(StructureTemplate.class)
public abstract interface StructureTemplateAccessor {
    @Accessor("size")
    public void setSize(Vec3i size);

    @Accessor("palettes")
    List<StructureTemplate.Palette> getPalettes();

    @Accessor("entityInfoList")
    List<StructureTemplate.StructureEntityInfo> getEntityInfoList();

    @Invoker("addToLists")
    public void invokeAddToLists(StructureTemplate.StructureBlockInfo structureBlockInfo, List<StructureTemplate.StructureBlockInfo> list, List<StructureTemplate.StructureBlockInfo> list2, List<StructureTemplate.StructureBlockInfo> list3);

    @Invoker("buildInfoList")
    public List<StructureTemplate.StructureBlockInfo> invokeBuildInfoList(List<StructureTemplate.StructureBlockInfo> list, List<StructureTemplate.StructureBlockInfo> list2, List<StructureTemplate.StructureBlockInfo> list3);

    @Invoker("fillEntityList")
    public void invokeFillEntityList(Level level, BlockPos blockPos, BlockPos blockPos2);
}


