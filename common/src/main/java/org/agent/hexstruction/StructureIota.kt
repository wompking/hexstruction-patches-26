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

// StructureIota should store a REFERENCE to a stored structure instead of the structure template itself (to prevent duplication)
// If it doesn't here, that's because debug and to-do, etc.
class StructureIota(structure: StructureTemplate) : Iota(TYPE, structure) {
    override fun isTruthy() = true
    override fun toleratesOther(that: Iota) = typesMatch(this, that) && this.payload == (that as StructureIota).payload

    override fun serialize(): CompoundTag {
        val tag = (payload as StructureTemplate).save(CompoundTag())
        return tag
    }

    companion object {
        @JvmField
        val TYPE: IotaType<StructureIota> = object : IotaType<StructureIota>() {
            override fun deserialize(
                tag: Tag,
                world: ServerLevel
            ): StructureIota {
                var t = StructureTemplate()
                t.load(world.holderLookup(Registries.BLOCK), tag as CompoundTag)
                return StructureIota(t)
            }

            override fun display(tag: Tag?): Component {
                TODO("Not yet implemented")
                return Component.literal("debug").withStyle(ChatFormatting.DARK_GREEN)
            }

            override fun color(): Int {
                TODO("Not yet implemented")
                return 0x118840
            }
        }
    }
}