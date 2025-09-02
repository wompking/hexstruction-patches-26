package org.agent.hexstruction;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import kotlin.Pair;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.Vec3;

public final class Utils {
    public static Vec3i GetVec3i(Vec3 vector) {
        int x = (int)Math.floor(vector.x);
        int y = (int)Math.floor(vector.y);
        int z = (int)Math.floor(vector.z);
        return new Vec3i(x,y,z);
    }

    public static Pair<Boolean, Vec3> CheckAmbitFromBoundingBox(BoundingBox bb, CastingEnvironment env) {
         for (int i : new int[]{bb.minX(), bb.maxX()})
            for (int j : new int[]{bb.minY(), bb.maxY()})
                for (int k : new int[]{bb.minZ(), bb.maxZ()}) {
                    Vec3 pos = new Vec3(i, j, k);
                    if (!env.isVecInAmbit(pos))
                        return new Pair<>(false, pos);
                }
        return new Pair<>(true, Vec3.ZERO);
    }

    public static int GetBlockCount(CompoundTag structureNBT) {
        ListTag blocks = structureNBT.getList("blocks", 10);
        return blocks.size();
    }
}
