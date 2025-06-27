@file:JvmName("HexstructionAbstractionsImpl")

package org.agent.hexstruction.fabric

import org.agent.hexstruction.registry.HexstructionRegistrar
import net.minecraft.core.Registry

fun <T : Any> initRegistry(registrar: HexstructionRegistrar<T>) {
    val registry = registrar.registry
    registrar.init { id, value -> Registry.register(registry, id, value) }
}
