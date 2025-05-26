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
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings
import net.minecraft.world.phys.Vec3
import org.agent.hexstruction.StructureIota
import org.agent.hexstruction.Utils

// todo: adjust cost based on targeted blocks
// todo: load from a reference and clear it
class OpLoadStructure : ConstMediaAction {
    override val argc = 2
    override val mediaCost = MediaConstants.CRYSTAL_UNIT

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val origin = Utils.GetBlockPos((args[0] as Vec3Iota).vec3)
        val structure = (args[1] as StructureIota).structure

        val settings = StructurePlaceSettings()

        structure.placeInWorld(env.world, origin, origin, settings, env.world.random, Block.UPDATE_CLIENTS)

        return listOf()
    }
}