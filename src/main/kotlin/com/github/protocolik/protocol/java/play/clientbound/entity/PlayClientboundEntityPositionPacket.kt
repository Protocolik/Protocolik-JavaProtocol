package com.github.protocolik.protocol.java.play.clientbound.entity

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayClientboundEntityPositionPacket
@JvmOverloads
constructor(
        var entityId: Int = 0,
        var moveX: Double = 0.0,
        var moveY: Double = 0.0,
        var moveZ: Double = 0.0,
        var onGround: Boolean = true
) : Packet(PacketType.PLAY_CLIENTBOUND_ENTITY_POSITION) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                entityId = byteBuf.readVarInt()
                moveX = byteBuf.readShort() / 4096.0
                moveY = byteBuf.readShort() / 4096.0
                moveZ = byteBuf.readShort() / 4096.0
                onGround = byteBuf.readBoolean()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                entityId = byteBuf.readVarInt()
                moveX = byteBuf.readByte() / 32.0
                moveY = byteBuf.readByte() / 32.0
                moveZ = byteBuf.readByte() / 32.0
                onGround = byteBuf.readBoolean()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                entityId = byteBuf.readInt()
                moveX = byteBuf.readByte() / 32.0
                moveY = byteBuf.readByte() / 32.0
                moveZ = byteBuf.readByte() / 32.0
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                byteBuf.writeVarInt(entityId)
                byteBuf.writeShort((moveX * 4096).toInt())
                byteBuf.writeShort((moveY * 4096).toInt())
                byteBuf.writeShort((moveZ * 4096).toInt())
                byteBuf.writeBoolean(onGround)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                byteBuf.writeVarInt(entityId)
                byteBuf.writeByte((moveX * 32).toInt())
                byteBuf.writeByte((moveY * 32).toInt())
                byteBuf.writeByte((moveZ * 32).toInt())
                byteBuf.writeBoolean(onGround)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                byteBuf.writeInt(entityId)
                byteBuf.writeByte((moveX * 32).toInt())
                byteBuf.writeByte((moveY * 32).toInt())
                byteBuf.writeByte((moveZ * 32).toInt())
            }
        }
    }
}