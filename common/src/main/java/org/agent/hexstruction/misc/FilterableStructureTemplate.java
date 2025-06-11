package org.agent.hexstruction.misc;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.agent.hexstruction.mixin.PaletteAccessor;
import org.agent.hexstruction.mixin.StructureTemplateAccessor;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.util.List;

public class FilterableStructureTemplate extends StructureTemplate {

    public void fillFromWorld(Level level, BlockPos blockPos, Vec3i vec3i, boolean bl, BlockCheckerInterface blockChecker) {
        if (vec3i.getX() >= 1 && vec3i.getY() >= 1 && vec3i.getZ() >= 1) {
            BlockPos blockPos2 = blockPos.offset(vec3i).offset(-1, -1, -1);
            List<StructureBlockInfo> list = Lists.newArrayList();
            List<StructureBlockInfo> list2 = Lists.newArrayList();
            List<StructureBlockInfo> list3 = Lists.newArrayList();
            BlockPos blockPos3 = new BlockPos(Math.min(blockPos.getX(), blockPos2.getX()), Math.min(blockPos.getY(), blockPos2.getY()), Math.min(blockPos.getZ(), blockPos2.getZ()));
            BlockPos blockPos4 = new BlockPos(Math.max(blockPos.getX(), blockPos2.getX()), Math.max(blockPos.getY(), blockPos2.getY()), Math.max(blockPos.getZ(), blockPos2.getZ()));
            ((StructureTemplateAccessor) this).setSize(vec3i);

            for(BlockPos blockPos5 : BlockPos.betweenClosed(blockPos3, blockPos4)) {
                BlockPos blockPos6 = blockPos5.subtract(blockPos3);
                BlockState blockState = level.getBlockState(blockPos5);
                if (blockChecker.checkBlock(blockState, blockPos5)) {
                    BlockEntity blockEntity = level.getBlockEntity(blockPos5);
                    StructureBlockInfo structureBlockInfo;
                    if (blockEntity != null) {
                        structureBlockInfo = new StructureBlockInfo(blockPos6, blockState, blockEntity.saveWithId());
                    } else {
                        structureBlockInfo = new StructureBlockInfo(blockPos6, blockState, (CompoundTag)null);
                    }

                    ((StructureTemplateAccessor) this).invokeAddToLists(structureBlockInfo, list, list2, list3);
                }
            }

            List<StructureBlockInfo> list4 = ((StructureTemplateAccessor) this).invokeBuildInfoList(list, list2, list3);

            List<StructureTemplate.Palette> palettes = ((StructureTemplateAccessor) this).getPalettes();

            palettes.clear();
            palettes.add(PaletteAccessor.create(list4));

            if (bl) {
                ((StructureTemplateAccessor) this).invokeFillEntityList(level, blockPos3, blockPos4.offset(1, 1, 1));
            } else {
                ((StructureTemplateAccessor) this).getEntityInfoList().clear();
            }
        }
    }
}
