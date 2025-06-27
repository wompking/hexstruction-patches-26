package org.agent.hexstruction.networking.msg

import dev.architectury.networking.NetworkChannel
import dev.architectury.networking.NetworkManager.PacketContext
import org.agent.hexstruction.Hexstruction
import org.agent.hexstruction.networking.HexstructionNetworking
import org.agent.hexstruction.networking.handler.applyOnClient
import org.agent.hexstruction.networking.handler.applyOnServer
import net.fabricmc.api.EnvType
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.level.ServerPlayer
import java.util.function.Supplier

sealed interface HexstructionMessage

sealed interface HexstructionMessageC2S : HexstructionMessage {
    fun sendToServer() {
        HexstructionNetworking.CHANNEL.sendToServer(this)
    }
}

sealed interface HexstructionMessageS2C : HexstructionMessage {
    fun sendToPlayer(player: ServerPlayer) {
        HexstructionNetworking.CHANNEL.sendToPlayer(player, this)
    }

    fun sendToPlayers(players: Iterable<ServerPlayer>) {
        HexstructionNetworking.CHANNEL.sendToPlayers(players, this)
    }
}

sealed interface HexstructionMessageCompanion<T : HexstructionMessage> {
    val type: Class<T>

    fun decode(buf: FriendlyByteBuf): T

    fun T.encode(buf: FriendlyByteBuf)

    fun apply(msg: T, supplier: Supplier<PacketContext>) {
        val ctx = supplier.get()
        when (ctx.env) {
            EnvType.SERVER, null -> {
                Hexstruction.LOGGER.debug("Server received packet from {}: {}", ctx.player.name.string, this)
                when (msg) {
                    is HexstructionMessageC2S -> msg.applyOnServer(ctx)
                    else -> Hexstruction.LOGGER.warn("Message not handled on server: {}", msg::class)
                }
            }
            EnvType.CLIENT -> {
                Hexstruction.LOGGER.debug("Client received packet: {}", this)
                when (msg) {
                    is HexstructionMessageS2C -> msg.applyOnClient(ctx)
                    else -> Hexstruction.LOGGER.warn("Message not handled on client: {}", msg::class)
                }
            }
        }
    }

    fun register(channel: NetworkChannel) {
        channel.register(type, { msg, buf -> msg.encode(buf) }, ::decode, ::apply)
    }
}
