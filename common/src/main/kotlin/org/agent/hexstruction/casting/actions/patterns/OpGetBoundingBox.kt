package org.agent.hexstruction.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota
import at.petrak.hexcasting.api.utils.asDouble
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import org.agent.hexstruction.getStructureNBT

object OpGetBoundingBox : ConstMediaAction {
    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val structureNBT = args.getStructureNBT(0, argc, env.world)

        var minX = 0.0
        var minY = 0.0
        var minZ = 0.0
        var maxX = 0.0
        var maxY = 0.0
        var maxZ = 0.0

        val blocks = structureNBT.getList("blocks", 10)
        for (tag in blocks) {
            val blockInts = (tag as CompoundTag).get("pos") as ListTag
            val x = blockInts[0].asDouble
            val y = blockInts[1].asDouble
            val z = blockInts[2].asDouble

            if (x < minX) minX = x
            if (x > maxX) maxX = x
            if (y < minY) minY = y
            if (y > maxY) maxY = y
            if (z < minZ) minZ = z
            if (z > maxZ) maxZ = z
        }

        val x = maxX - minX + 1
        val y = maxY - minY + 1
        val z = maxZ - minZ + 1

        return listOf(ListIota(listOf(DoubleIota(x), DoubleIota(y), DoubleIota(z))))
    }
}