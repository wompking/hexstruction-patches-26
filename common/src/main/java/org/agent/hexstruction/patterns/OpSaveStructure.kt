package org.agent.hexstruction.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadBlock
import at.petrak.hexcasting.api.casting.mishaps.MishapBadLocation
import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.xplat.IXplatAbstractions
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3
import org.agent.hexstruction.StructureIota
import org.agent.hexstruction.StructureManager
import org.agent.hexstruction.Utils

// todo: adjust cost based on targeted blocks
// todo: claim integration (maybe done?)
// todo: make blacklisting a tag
class OpSaveStructure : ConstMediaAction {
    override val argc = 2
    override val mediaCost = MediaConstants.CRYSTAL_UNIT

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val LSW_bound = Utils.GetVec3i((args[0] as Vec3Iota).vec3)
        val UNE_bound = Utils.GetVec3i((args[1] as Vec3Iota).vec3)

        val bb = BoundingBox.fromCorners(LSW_bound, UNE_bound)

        val result = checkAmbitFromBoundingBox(bb, env)
        if (!result.first)
            throw MishapBadLocation(result.second)

        val origin = BlockPos(bb.minX(), bb.minY(), bb.minZ())
        val bounds = BlockPos(bb.xSpan, bb.ySpan, bb.zSpan)

        val structure = StructureTemplate()

        var blockPos = BlockPos(0, 0, 0)
        for (i in bb.minX()..bb.maxX()) {
            for (j in bb.minY()..bb.maxY()) {
                for (k in bb.minZ()..bb.maxZ()) {
                    val pos = BlockPos(i, j, k)
                    blockPos = pos
                    val blockState = env.world.getBlockState(pos)
                    if (blockState.block.defaultDestroyTime() == -1f || !IXplatAbstractions.INSTANCE.isBreakingAllowed(
                        env.world,
                        pos,
                        env.world.getBlockState(pos),
                        env.castingEntity as? ServerPlayer
                    ) || blockState.`is`(Blocks.BUDDING_AMETHYST))
                        throw MishapBadBlock(blockPos, Component.literal("breakable block"))
                }
            }
        }


        structure.fillFromWorld(env.world, origin, bounds, false, Blocks.AIR)
        for (i in bb.minX()..bb.maxX()) {
            for (j in bb.minY()..bb.maxY()) {
                for (k in bb.minZ()..bb.maxZ()) {
                    val pos = BlockPos(i, j, k)
                    val blockState = env.world.getBlockState(pos)
                    if (!blockState.isAir /*&& !blockState.`is`(Blocks.BEDROCK)*/)
                        env.world.removeBlock(pos, false)
                }
            }
        }

        val uuid = StructureManager.SaveStructure(env.world, structure)

        return listOf(StructureIota(uuid, env.world))
    }

    fun checkAmbitFromBoundingBox(bb: BoundingBox, env: CastingEnvironment): Pair<Boolean, Vec3> {
        for (i in listOf(bb.minX(), bb.maxX()))
            for (j in listOf(bb.minY(), bb.maxY()))
                for (k in listOf(bb.minZ(), bb.maxZ()))
                {
                    val pos = Vec3(i.toDouble(), j.toDouble(), k.toDouble())
                    if (!env.isVecInAmbit(pos))
                        return Pair(false, pos)
                }
        return Pair(true, Vec3.ZERO)
    }
}