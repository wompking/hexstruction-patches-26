package org.agent.hexstruction.networking.handler

import dev.architectury.networking.NetworkManager.PacketContext
import org.agent.hexstruction.config.HexstructionConfig
import org.agent.hexstruction.networking.msg.*

fun HexstructionMessageS2C.applyOnClient(ctx: PacketContext) = ctx.queue {
    when (this) {
        is MsgSyncConfigS2C -> {
            HexstructionConfig.onSyncConfig(serverConfig)
        }

        // add more client-side message handlers here
    }
}
