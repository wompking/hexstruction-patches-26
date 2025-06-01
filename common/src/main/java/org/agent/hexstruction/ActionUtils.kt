package org.agent.hexstruction

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.level.Level
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings
import java.util.UUID

fun List<Iota>.getStructureIota(idx: Int, argc: Int = 0): StructureIota {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is StructureIota) {
        return x
    } else {
        throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "structure")
    }
}

fun List<Iota>.getStructureUUID(idx: Int, argc: Int = 0): UUID {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is StructureIota) {
        return x.uuid
    } else {
        throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "structure")
    }
}

fun List<Iota>.getStructureSettings(idx: Int, argc: Int = 0): StructurePlaceSettings {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is StructureIota) {
        return x.settings
    } else {
        throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "structure")
    }
}

fun List<Iota>.getStructureNBT(idx: Int, argc: Int = 0, world: Level): CompoundTag {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is StructureIota) {
        val structureNBT = StructureManager.GetStructure(world, x.uuid)
        if (structureNBT == null) {
            throw MishapInvalidIota(x, if (argc == 0) idx else argc - (idx + 1), Component.literal("a linked structure"))
        }
        return structureNBT
    } else {
        throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "structure")
    }
}