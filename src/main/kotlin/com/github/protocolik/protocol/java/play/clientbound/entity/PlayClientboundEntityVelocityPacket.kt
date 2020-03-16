package com.github.protocolik.protocol.java.play.clientbound.entity

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayClientboundEntityVelocityPacket
@JvmOverloads
constructor(
        var entityId: Int = 0,
        var velocityX: Double = 0.0,
        var velocityY: Double = 0.0,
        var velocityZ: Double = 0.0
) : Packet(PacketType.PLAY_CLIENTBOUND_ENTITY_VELOCITY) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                entityId = byteBuf.readVarInt()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                entityId = byteBuf.readInt()
            }
        }
        velocityX = byteBuf.readShort() / 8000.0
        velocityY = byteBuf.readShort() / 8000.0
        velocityZ = byteBuf.readShort() / 8000.0
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
        byteBuf.writeShort((velocityX * 8000).toInt())
        byteBuf.writeShort((velocityY * 8000).toInt())
        byteBuf.writeShort((velocityZ * 8000).toInt())
    }
}