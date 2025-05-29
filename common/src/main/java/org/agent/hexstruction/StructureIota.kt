package org.agent.hexstruction

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexcasting.api.utils.putCompound
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
import net.minecraft.world.level.block.Mirror
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings
import java.util.UUID

class StructureIota(structureUUID: UUID, val settings: StructurePlaceSettings, val world: Level) : Iota(TYPE, structureUUID) {
    override fun isTruthy() = true
    override fun toleratesOther(that: Iota) = typesMatch(this, that) && this.payload == (that as StructureIota).payload
    val uuid = payload as UUID

    override fun serialize(): CompoundTag {
        val tag = CompoundTag()
        tag.putUUID("uuid", uuid)
        tag.putBoolean("referenceExists", StructureManager.CheckStructureSaved(world, uuid))

        val settingsTag = CompoundTag()
        settingsTag.putString("mirror", settings.mirror.toString())
        settingsTag.putString("rotation", settings.rotation.toString())

        tag.putCompound("settings", settingsTag)

        return tag
    }

    companion object {
        @JvmField
        val TYPE: IotaType<StructureIota> = object : IotaType<StructureIota>() {
            override fun deserialize(tag: Tag, world: ServerLevel) : StructureIota {
                tag as CompoundTag
                val uuid = tag.getUUID("uuid")
                tag.putBoolean("referenceExists", StructureManager.CheckStructureSaved(world, uuid))

                val settingsTag = tag.getCompound("settings")
                val settings = StructurePlaceSettings()
                settings.mirror = Mirror.valueOf(settingsTag.getString("mirror"))
                settings.rotation = Rotation.valueOf(settingsTag.getString("rotation"))

                return StructureIota(uuid, settings, world)
            }

            override fun display(tag: Tag) : Component {
                val uuid = (tag as CompoundTag).getUUID("uuid")
                val referenceExists = tag.getBoolean("referenceExists")
                if (referenceExists) {
                    val settingsTag = tag.getCompound("settings")
                    val mirror = settingsTag.getString("mirror")
                    val rotation = settingsTag.getString("rotation")
                    return Component.literal("Structure ${uuid.toString().substring(0, 8)} [MIRROR=$mirror, ROTATION=$rotation]").withStyle(ChatFormatting.DARK_GREEN)
                }
                return Component.literal("No Structure").withStyle(ChatFormatting.DARK_GREEN)
            }

            override fun color() = 0x118840
        }
    }
}