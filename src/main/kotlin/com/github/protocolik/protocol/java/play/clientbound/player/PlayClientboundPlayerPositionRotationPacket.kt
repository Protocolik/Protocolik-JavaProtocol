package com.github.protocolik.protocol.java.play.clientbound.player

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayClientboundPlayerPositionRotationPacket
@JvmOverloads
constructor(
        var x: Double = 0.0,
        var y: Double = 0.0,
        var z: Double = 0.0,
        var yaw: Float = 0.0f,
        var pitch: Float = 0.0f,
        var onGround: Boolean = true,
        var flags: Int = 0,
        var teleportId: Int = 0
) : Packet(PacketType.PLAY_CLIENTBOUND_PLAYER_POSITION_ROTATION) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        x = byteBuf.readDouble()
        y = byteBuf.readDouble()
        z = byteBuf.readDouble()
        yaw = byteBuf.readFloat()
        pitch = byteBuf.readFloat()
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                flags = byteBuf.readByte().toInt()
                teleportId = byteBuf.readVarInt()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                flags = byteBuf.readByte().toInt()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                onGround = byteBuf.readBoolean()
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeDouble(x)
        byteBuf.writeDouble(y)
        byteBuf.writeDouble(z)
        byteBuf.writeFloat(yaw)
        byteBuf.writeFloat(pitch)
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                byteBuf.writeByte(flags)
                byteBuf.writeVarInt(teleportId)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                byteBuf.writeByte(flags)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                byteBuf.writeBoolean(onGround)
            }
        }
    }
}