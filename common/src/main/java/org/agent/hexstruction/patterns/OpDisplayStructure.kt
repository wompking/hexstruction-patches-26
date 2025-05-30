package org.agent.hexstruction.patterns

import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadBlock
import at.petrak.hexcasting.api.casting.mishaps.MishapBadLocation
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.api.utils.asCompound
import at.petrak.hexcasting.api.utils.asInt
import at.petrak.hexcasting.xplat.IXplatAbstractions
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Display
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.DirectionalPlaceContext
import net.minecraft.world.level.block.Mirror
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate
import net.minecraft.world.phys.Vec3
import org.agent.hexstruction.StructureIota
import org.agent.hexstruction.StructureManager
import org.agent.hexstruction.Utils
import org.agent.hexstruction.misc.TimedBlockDisplay
import org.agent.hexstruction.mixin.BlockDisplayInvoker
import org.agent.hexstruction.patterns.OpLoadStructure.Spell

// todo: limit lifetime to 1 hour (72000 ticks)
// todo: invalid iota type checks
class OpDisplayStructure : SpellAction {
    override val argc = 3

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val origin = Utils.GetBlockPos((args[0] as Vec3Iota).vec3)
        val structureIota = args[1] as StructureIota
        val uuid = structureIota.uuid
        val structureNBT = StructureManager.GetStructure(env.world, uuid)
        if (structureNBT == null) {
            throw MishapInvalidIota(args[1] as StructureIota, 0, Component.literal("a linked structure"))
        }

        val structure = StructureTemplate()
        structure.load(env.world.holderLookup(Registries.BLOCK), structureNBT)

        val settings = structureIota.settings

        val bb = structure.getBoundingBox(settings, origin)
        val result = Utils.CheckAmbitFromBoundingBox(bb, env)
        if (!result.first)
            throw MishapBadLocation(result.second)

        return SpellAction.Result(
            Spell(structure, settings, origin, structureNBT, (args[2] as DoubleIota).double),
            (bb.xSpan * bb.ySpan * bb.zSpan * MediaConstants.DUST_UNIT) / 100,
            listOf(ParticleSpray.burst(Vec3(bb.center.x.toDouble(), bb.center.y.toDouble(), bb.center.z.toDouble()), 1.0))
        )
    }

    private data class Spell(val structure: StructureTemplate, val settings: StructurePlaceSettings, val origin: BlockPos, val structureNBT: CompoundTag, val lifeTime: Double) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            val newOrigin = structure.getZeroPositionWithTransform(origin, settings.mirror, settings.rotation)

            val palette = structureNBT.getList("palette", 10)
            val blocks = structureNBT.getList("blocks", 10)
            for (tag in blocks) {
                val blockInts = (tag as CompoundTag).get("pos") as ListTag
                var x = blockInts[0].asInt
                val y = blockInts[1].asInt
                var z = blockInts[2].asInt

                when (settings.mirror) {
                    Mirror.NONE -> null
                    Mirror.LEFT_RIGHT -> z *= -1
                    Mirror.FRONT_BACK -> x *= -1
                }

                val pos = BlockPos(x, y, z).rotate(settings.rotation).offset(newOrigin.x, newOrigin.y, newOrigin.z)
                val blockName = palette[tag.getInt("state")].asCompound.getString("Name")
                val blockState = BuiltInRegistries.BLOCK.get(ResourceLocation(blockName)).defaultBlockState()
                val blockDisplay = TimedBlockDisplay(EntityType.BLOCK_DISPLAY, env.world, env.world.gameTime, lifeTime.toLong()).apply {
                    setPos(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble())
                }

                (blockDisplay as BlockDisplayInvoker).invokeSetBlockState(blockState)
                env.world.addFreshEntity(blockDisplay)
                blockDisplay.tick()
            }
        }
    }
}