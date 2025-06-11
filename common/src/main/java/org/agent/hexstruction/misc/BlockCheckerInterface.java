package org.agent.hexstruction.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public interface BlockCheckerInterface {
    public boolean checkBlock(BlockState state, BlockPos pos);
}
