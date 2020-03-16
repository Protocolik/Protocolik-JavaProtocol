package com.github.protocolik.protocol.java.play.clientbound.entity

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayClientboundEntityRotationPacket
@JvmOverloads
constructor(
        var entityId: Int = 0,
        var yaw: Float = 0.0f,
        var pitch: Float = 0.0f,
        var onGround: Boolean = true
) : Packet(PacketType.PLAY_CLIENTBOUND_ENTITY_ROTATION) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                entityId = byteBuf.readVarInt()
                yaw = byteBuf.readByte() * 360 / 256f
                pitch = byteBuf.readByte() * 360 / 256f
                onGround = byteBuf.readBoolean()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                entityId = byteBuf.readInt()
                yaw = byteBuf.readByte() * 360 / 256f
                pitch = byteBuf.readByte() * 360 / 256f
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                byteBuf.writeVarInt(entityId)
                byteBuf.writeByte((yaw * 256 / 360).toInt())
                byteBuf.writeByte((pitch * 256 / 360).toInt())
                byteBuf.writeBoolean(onGround)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                byteBuf.writeInt(entityId)
                byteBuf.writeByte((yaw * 256 / 360).toInt())
                byteBuf.writeByte((pitch * 256 / 360).toInt())
            }
        }
    }
}