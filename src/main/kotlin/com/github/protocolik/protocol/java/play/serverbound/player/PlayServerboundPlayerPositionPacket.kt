package com.github.protocolik.protocol.java.play.serverbound.player

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import io.netty.buffer.ByteBuf

data class PlayServerboundPlayerPositionPacket
@JvmOverloads
constructor(
        var x: Double = 0.0,
        var y: Double = 0.0,
        var z: Double = 0.0,
        var onGround: Boolean = true
) : Packet(PacketType.PLAY_SERVERBOUND_PLAYER_POSITION) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                x = byteBuf.readDouble()
                y = byteBuf.readDouble()
                z = byteBuf.readDouble()
                onGround = byteBuf.readBoolean()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                x = byteBuf.readDouble()
                y = byteBuf.readDouble() // Feet position
                byteBuf.readDouble() // Head position (not used)
                z = byteBuf.readDouble()
                onGround = byteBuf.readBoolean()
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_8 -> {
                byteBuf.writeDouble(x)
                byteBuf.writeDouble(y)
                byteBuf.writeDouble(z)
                byteBuf.writeBoolean(onGround)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                byteBuf.writeDouble(x)
                byteBuf.writeDouble(y)
                byteBuf.writeDouble(y + 1.62) // Head position (not used)
                byteBuf.writeDouble(z)
                byteBuf.writeBoolean(onGround)
            }
        }
    }
}