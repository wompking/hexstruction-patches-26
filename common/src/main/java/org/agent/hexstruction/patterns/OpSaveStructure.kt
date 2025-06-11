package org.agent.hexstruction.patterns

import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadBlock
import at.petrak.hexcasting.api.casting.mishaps.MishapBadLocation
import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.xplat.IXplatAbstractions
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings
import net.minecraft.world.phys.Vec3
import org.agent.hexstruction.StructureIota
import org.agent.hexstruction.StructureManager
import org.agent.hexstruction.Utils
import org.agent.hexstruction.tags.HexstructionBlockTags
import java.util.UUID

// todo: claim integration (maybe done?)
// origin of structures is lower north-west
class OpSaveStructure : SpellAction {
    override val argc = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val LSW_bound = Utils.GetVec3i((args.getVec3(0, argc)))
        val UNE_bound = Utils.GetVec3i((args.getVec3(1, argc)))

        val bb = BoundingBox.fromCorners(LSW_bound, UNE_bound)

        val result = Utils.CheckAmbitFromBoundingBox(bb, env)
        if (!result.first)
            throw MishapBadLocation(result.second)

        var blockPos: BlockPos
        for (i in bb.minX()..bb.maxX()) {
            for (j in bb.minY()..bb.maxY()) {
                for (k in bb.minZ()..bb.maxZ()) {
                    val pos = BlockPos(i, j, k)
                    blockPos = pos
                    val blockState = env.world.getBlockState(pos)
                    if (blockState.block.defaultDestroyTime() == -1f || blockState.getDestroySpeed(env.world, pos) < 0f
                        || !IXplatAbstractions.INSTANCE.isBreakingAllowed(
                        env.world,
                        pos,
                        env.world.getBlockState(pos),
                        env.castingEntity as? ServerPlayer
                    ))
                        throw MishapBadBlock.of(blockPos, "breakable block")
                    if (blockState.`is`(HexstructionBlockTags.STRUCTURE_SAVE_BANNED))
                        throw MishapBadBlock.of(blockPos, "non-budding block")
                }
            }
        }

        return SpellAction.Result(
            Spell(bb, this.argc),
            (bb.xSpan * bb.ySpan * bb.zSpan * MediaConstants.DUST_UNIT) / 8,
            listOf(ParticleSpray.burst(Vec3.atCenterOf(bb.center), 1.0))
        )
    }

    private data class Spell(val bb: BoundingBox, val argc: Int) : RenderedSpell {
        var uuid: UUID? = null

        override fun cast(env: CastingEnvironment) {
            val origin = BlockPos(bb.minX(), bb.minY(), bb.minZ())
            val bounds = BlockPos(bb.xSpan, bb.ySpan, bb.zSpan)

            val structure = StructureTemplate()

            structure.fillFromWorld(env.world, origin, bounds, false, Blocks.AIR)
            for (i in bb.minX()..bb.maxX()) {
                for (j in bb.minY()..bb.maxY()) {
                    for (k in bb.minZ()..bb.maxZ()) {
                        val pos = BlockPos(i, j, k)
                        val blockState = env.world.getBlockState(pos)
                        if (blockState.hasBlockEntity())
                            env.world.removeBlockEntity(pos)
                        if (!blockState.isAir)
                            env.world.removeBlock(pos, false)
                    }
                }
            }
            this.uuid = StructureManager.SaveStructure(env.world, structure)
        }

        override fun cast(env: CastingEnvironment, image: CastingImage): CastingImage? {
            cast(env)
            val stack = image.stack.toMutableList()
            stack.add(StructureIota(uuid!!, StructurePlaceSettings(), env.world))

            val image2 = image.copy(stack = stack)

            return image2
        }
    }
}