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
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.phys.Vec3
import org.agent.hexstruction.StructureIota
import org.agent.hexstruction.StructureManager
import org.agent.hexstruction.Utils

// todo: break blocks when saving
// todo: adjust cost based on targeted blocks
class OpSaveStructure : ConstMediaAction {
    override val argc = 2
    override val mediaCost = MediaConstants.CRYSTAL_UNIT

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val LSW_bound = Utils.GetVec3i((args[0] as Vec3Iota).vec3)
        val UNE_bound = Utils.GetVec3i((args[1] as Vec3Iota).vec3)

        val bb = BoundingBox.fromCorners(LSW_bound, UNE_bound)
        val origin = BlockPos(bb.minX(), bb.minY(), bb.minZ())
        val bounds = BlockPos(bb.xSpan, bb.ySpan, bb.zSpan)

        val structure = StructureTemplate()

        structure.fillFromWorld(env.world, origin, bounds, false, Blocks.AIR)

        val uuid = StructureManager.SaveStructure(env.world, structure)

        return listOf(StructureIota(uuid, env.world))
    }
}