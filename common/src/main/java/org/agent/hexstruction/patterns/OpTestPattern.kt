package org.agent.hexstruction.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.misc.MediaConstants

class OpTestPattern : ConstMediaAction {
    override val argc = 0
    override val mediaCost = MediaConstants.CRYSTAL_UNIT

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        return listOf(DoubleIota(0.1234567))
    }
}