package org.agent.hexstruction.registry

import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.common.lib.HexRegistries
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import org.agent.hexstruction.StructureIota

object HexstructionIotaTypes : HexstructionRegistrar<IotaType<*>>(
    HexRegistries.IOTA_TYPE,
    { HexIotaTypes.REGISTRY }
) {
    val STRUCTURE = register("structure") { StructureIota.TYPE }
}