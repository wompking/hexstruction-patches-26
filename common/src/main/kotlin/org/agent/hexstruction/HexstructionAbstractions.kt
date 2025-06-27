@file:JvmName("HexstructionAbstractions")

package org.agent.hexstruction

import dev.architectury.injectables.annotations.ExpectPlatform
import org.agent.hexstruction.registry.HexstructionRegistrar

fun initRegistries(vararg registries: HexstructionRegistrar<*>) {
    for (registry in registries) {
        initRegistry(registry)
    }
}

@ExpectPlatform
fun <T : Any> initRegistry(registrar: HexstructionRegistrar<T>) {
    throw AssertionError()
}
