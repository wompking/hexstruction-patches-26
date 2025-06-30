package org.agent.hexstruction.misc;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.List;

public class FilterableStructureTemplate extends StructureTemplate {

    public void fillFromWorld(Level level, BlockPos blockPos, Vec3i size, boolean withEntities, BlockCheckerInterface blockChecker) {
        if (size.getX() >= 1 && size.getY() >= 1 && size.getZ() >= 1) {
            BlockPos blockPos2 = blockPos.offset(size).offset(-1, -1, -1);
            List<StructureBlockInfo> list = Lists.newArrayList();
            List<StructureBlockInfo> list2 = Lists.newArrayList();
            List<StructureBlockInfo> list3 = Lists.newArrayList();
            BlockPos blockPos3 = new BlockPos(Math.min(blockPos.getX(), blockPos2.getX()), Math.min(blockPos.getY(), blockPos2.getY()), Math.min(blockPos.getZ(), blockPos2.getZ()));
            BlockPos blockPos4 = new BlockPos(Math.max(blockPos.getX(), blockPos2.getX()), Math.max(blockPos.getY(), blockPos2.getY()), Math.max(blockPos.getZ(), blockPos2.getZ()));
            this.size = size;

            for(BlockPos blockPos5 : BlockPos.betweenClosed(blockPos3, blockPos4)) {
                BlockPos blockPos6 = blockPos5.subtract(blockPos3);
                BlockState blockState = level.getBlockState(blockPos5);
                if (blockChecker.checkBlock(blockState, blockPos5)) {
                    BlockEntity blockEntity = level.getBlockEntity(blockPos5);
                    StructureBlockInfo structureBlockInfo;
                    if (blockEntity != null) {
                        structureBlockInfo = new StructureBlockInfo(blockPos6, blockState, blockEntity.saveWithId());
                    } else {
                        structureBlockInfo = new StructureBlockInfo(blockPos6, blockState, null);
                    }

                    addToLists(structureBlockInfo, list, list2, list3);
                }
            }

            List<StructureBlockInfo> list4 = buildInfoList(list, list2, list3);

            this.palettes.clear();
            this.palettes.add(new Palette(list4));

            if (withEntities) {
                this.fillEntityList(level, blockPos3, blockPos4.offset(1, 1, 1));
            } else {
               this.entityInfoList.clear();
            }
        }
    }
}
