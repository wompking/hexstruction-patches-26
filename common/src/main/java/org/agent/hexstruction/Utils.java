package org.agent.hexstruction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;

public final class Utils {
    public static Vec3i GetVec3i(Vec3 vector) {
        int x = (int)vector.x;
        if (x < 0)
            x--;

        int y = (int)vector.y;
        if (y < 0)
            y--;

        int z = (int)vector.z;
        if (z < 0)
            z--;

        return new Vec3i(x,y,z);
    }

    public static BlockPos GetBlockPos(Vec3 vector) {
        return new BlockPos(GetVec3i(vector));
    }
}
