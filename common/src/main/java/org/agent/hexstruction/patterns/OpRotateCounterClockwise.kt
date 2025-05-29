package org.agent.hexstruction.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import net.minecraft.world.level.block.Rotation
import org.agent.hexstruction.StructureIota

class OpRotateCounterClockwise : ConstMediaAction {
    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val structureIota = args[0] as StructureIota
        structureIota.settings.rotation = Rotation.entries[(structureIota.settings.rotation.ordinal + 3) % 4]
        return listOf(structureIota)
    }
}