package org.agent.hexstruction.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i
import net.minecraft.world.phys.Vec3
import org.agent.hexstruction.StructureIota

// todo: break blocks when saving
// todo: adjust cost based on targeted blocks
// todo: save a reference to the iota, not the structure itself
class OpSaveStructure : ConstMediaAction {
    override val argc = 2
    override val mediaCost = MediaConstants.CRYSTAL_UNIT

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val LSW_bound = getBlockPos((args[0] as Vec3Iota).vec3)
        val UNE_bound = getBlockPos((args[1] as Vec3Iota).vec3)

        val bb = BoundingBox.fromCorners(LSW_bound, UNE_bound)
        val origin = BlockPos(bb.minX(), bb.maxY(), bb.minZ())
        val bounds = BlockPos(bb.xSpan, bb.ySpan, bb.zSpan)

        val structure = StructureTemplate()

        structure.fillFromWorld(env.world, origin, bounds, false, Blocks.AIR)

        return listOf(StructureIota(structure))
    }

    fun getBlockPos(vector: Vec3): Vec3i {
        return Vec3i(vector.x.toInt(), vector.y.toInt(), vector.z.toInt())
    }
}