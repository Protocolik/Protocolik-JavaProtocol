package com.github.protocolik.protocol.java.play.clientbound.entity

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayClientboundEntityHeadRotationPacket
@JvmOverloads
constructor(
        var entityId: Int = 0,
        var headYaw: Int = 0
) : Packet(PacketType.PLAY_CLIENTBOUND_ENTITY_HEAD_ROTATION) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                entityId = byteBuf.readVarInt()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                entityId = byteBuf.readInt()
            }
        }
        headYaw = byteBuf.readByte().toInt()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                byteBuf.writeVarInt(entityId)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                byteBuf.writeInt(entityId)
            }
        }
        byteBuf.writeByte(headYaw)
    }
}