package org.agent.hexstruction.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadLocation
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.misc.MediaConstants
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor
import net.minecraft.world.phys.Vec3
import org.agent.hexstruction.StructureIota
import org.agent.hexstruction.StructureManager
import org.agent.hexstruction.Utils
import java.util.UUID

// todo: adjust cost based on targeted blocks
// todo: placement overlap checks, mishap on overlap instead of consuming the structure and deleting parts of the world
// todo: claim integration
class OpLoadStructure : ConstMediaAction {
    override val argc = 2
    override val mediaCost = MediaConstants.CRYSTAL_UNIT

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val origin = Utils.GetBlockPos((args[0] as Vec3Iota).vec3)
        val uuid = (args[1] as StructureIota).uuid
        val structureNBT = StructureManager.GetStructure(env.world, uuid)
        if (structureNBT == null) {
            throw MishapInvalidIota(args[1] as StructureIota, 0, Component.literal("a linked structure"))
        }

        val structure = StructureTemplate()
        structure.load(env.world.holderLookup(Registries.BLOCK), structureNBT)

        val settings = StructurePlaceSettings()

        val bb = structure.getBoundingBox(settings, origin)
        val result = checkAmbitFromBoundingBox(bb, env)
        if (!result.first)
            throw MishapBadLocation(result.second)

        structure.placeInWorld(env.world, origin, origin, settings, env.world.random, Block.UPDATE_CLIENTS)

        StructureManager.RemoveStructure(env.world, uuid)

        return listOf()
    }

    //todo: refactor out into Utils
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