package org.agent.hexstruction.networking

import dev.architectury.networking.NetworkChannel
import org.agent.hexstruction.Hexstruction
import org.agent.hexstruction.networking.msg.HexstructionMessageCompanion

object HexstructionNetworking {
    val CHANNEL: NetworkChannel = NetworkChannel.create(Hexstruction.id("networking_channel"))

    fun init() {
        for (subclass in HexstructionMessageCompanion::class.sealedSubclasses) {
            subclass.objectInstance?.register(CHANNEL)
        }
    }
}
