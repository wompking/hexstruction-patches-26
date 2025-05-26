package org.agent.hexstruction

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import net.minecraft.ChatFormatting
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.server.level.ServerLevel
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import java.util.UUID

// todo: figure out how to represent the data (display)
class StructureIota(structureUUID: UUID, world: Level) : Iota(TYPE, structureUUID) {
    override fun isTruthy() = true
    override fun toleratesOther(that: Iota) = typesMatch(this, that) && this.payload == (that as StructureIota).payload
    val uuid = payload as UUID
    val world = world

    override fun serialize(): CompoundTag {
        val tag = CompoundTag()
        tag.putUUID("uuid", uuid)
        tag.putBoolean("referenceExists", StructureManager.CheckStructureSaved(world, uuid))
        return tag
    }

    companion object {
        @JvmField
        val TYPE: IotaType<StructureIota> = object : IotaType<StructureIota>() {
            override fun deserialize(tag: Tag, world: ServerLevel) : StructureIota {
                tag as CompoundTag
                val uuid = tag.getUUID("uuid")
                tag.putBoolean("referenceExists", StructureManager.CheckStructureSaved(world, uuid))
                return StructureIota(uuid, world)
            }

            override fun display(tag: Tag) : Component {
                val uuid = (tag as CompoundTag).getUUID("uuid")
                val referenceExists = tag.getBoolean("referenceExists")
                if (referenceExists) {
                    return Component.literal(uuid.toString()).withStyle(ChatFormatting.DARK_GREEN)
                }
                return Component.literal("No Structure").withStyle(ChatFormatting.DARK_GREEN)
            }

            override fun color() = 0x118840
        }
    }
}