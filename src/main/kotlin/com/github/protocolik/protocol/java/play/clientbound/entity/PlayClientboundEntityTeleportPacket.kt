package com.github.protocolik.protocol.java.play.clientbound.entity

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayClientboundEntityTeleportPacket
@JvmOverloads
constructor(
        var entityId: Int = 0,
        var x: Double = 0.0,
        var y: Double = 0.0,
        var z: Double = 0.0,
        var yaw: Float = 0.0f,
        var pitch: Float = 0.0f,
        var onGround: Boolean = true
) : Packet(PacketType.PLAY_CLIENTBOUND_ENTITY_TELEPORT) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                entityId = byteBuf.readVarInt()
                x = byteBuf.readDouble()
                y = byteBuf.readDouble()
                z = byteBuf.readDouble()
                yaw = byteBuf.readByte() * 360 / 256f
                pitch = byteBuf.readByte() * 360 / 256f
                onGround = byteBuf.readBoolean()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                entityId = byteBuf.readVarInt()
                x = byteBuf.readInt() / 32.0
                y = byteBuf.readInt() / 32.0
                z = byteBuf.readInt() / 32.0
                yaw = byteBuf.readByte() * 360 / 256f
                pitch = byteBuf.readByte() * 360 / 256f
                onGround = byteBuf.readBoolean()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                entityId = byteBuf.readInt()
                x = byteBuf.readInt() / 32.0
                y = byteBuf.readInt() / 32.0
                z = byteBuf.readInt() / 32.0
                yaw = byteBuf.readByte() * 360 / 256f
                pitch = byteBuf.readByte() * 360 / 256f
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                byteBuf.writeVarInt(entityId)
                byteBuf.writeDouble(x)
                byteBuf.writeDouble(y)
                byteBuf.writeDouble(z)
                byteBuf.writeByte((yaw * 256 / 360).toInt())
                byteBuf.writeByte((pitch * 256 / 360).toInt())
                byteBuf.writeBoolean(onGround)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                byteBuf.writeVarInt(entityId)
                byteBuf.writeInt((x * 32).toInt())
                byteBuf.writeInt((y * 32).toInt())
                byteBuf.writeInt((z * 32).toInt())
                byteBuf.writeByte((yaw * 256 / 360).toInt())
                byteBuf.writeByte((pitch * 256 / 360).toInt())
                byteBuf.writeBoolean(onGround)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                byteBuf.writeInt(entityId)
                byteBuf.writeInt((x * 32).toInt())
                byteBuf.writeInt((y * 32).toInt())
                byteBuf.writeInt((z * 32).toInt())
                byteBuf.writeByte((yaw * 256 / 360).toInt())
                byteBuf.writeByte((pitch * 256 / 360).toInt())
            }
        }
    }
}