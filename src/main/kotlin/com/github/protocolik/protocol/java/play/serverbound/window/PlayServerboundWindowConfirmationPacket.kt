package com.github.protocolik.protocol.java.play.serverbound.window

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import io.netty.buffer.ByteBuf

data class PlayServerboundWindowConfirmationPacket
@JvmOverloads
constructor(
        var containerId: Int = 0,
        var actionId: Int = 0,
        var accepted: Boolean = true
) : Packet(PacketType.PLAY_SERVERBOUND_WINDOW_CONFIRMATION) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                containerId = byteBuf.readByte().toInt()
                actionId = byteBuf.readShort().toInt()
                accepted = byteBuf.readBoolean()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                containerId = byteBuf.readUnsignedByte().toInt()
                actionId = byteBuf.readShort().toInt()
                accepted = byteBuf.readBoolean()
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeByte(containerId)
        byteBuf.writeShort(actionId)
        byteBuf.writeBoolean(accepted)
    }
}