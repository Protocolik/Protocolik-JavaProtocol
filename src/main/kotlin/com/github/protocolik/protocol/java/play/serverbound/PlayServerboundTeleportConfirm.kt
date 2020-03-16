package com.github.protocolik.protocol.java.play.serverbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayServerboundTeleportConfirm
@JvmOverloads
constructor(
        var teleportId: Int = 0
) : Packet(PacketType.PLAY_SERVERBOUND_TELEPORT_CONFIRM) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        teleportId = byteBuf.readVarInt()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeVarInt(teleportId)
    }
}