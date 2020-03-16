package com.github.protocolik.protocol.java.play.clientbound.spawn

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf

data class PlayClientboundSpawnEntityExperienceOrbPacket
@JvmOverloads
constructor(
        var entityId: Int = 0,
        var x: Double = 0.0,
        var y: Double = 0.0,
        var z: Double = 0.0,
        var count: Int = 0
) : Packet(PacketType.PLAY_CLIENTBOUND_SPAWN_ENTITY_EXPERIENCE_ORB) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        entityId = byteBuf.readVarInt()
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                x = byteBuf.readDouble()
                y = byteBuf.readDouble()
                z = byteBuf.readDouble()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                x = byteBuf.readInt() / 32.0
                y = byteBuf.readInt() / 32.0
                z = byteBuf.readInt() / 32.0
            }
        }
        count = byteBuf.readShort().toInt()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeVarInt(entityId)
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                byteBuf.writeDouble(x)
                byteBuf.writeDouble(y)
                byteBuf.writeDouble(z)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                byteBuf.writeInt((x * 32).toInt())
                byteBuf.writeInt((y * 32).toInt())
                byteBuf.writeInt((z * 32).toInt())
            }
        }
        byteBuf.writeShort(count)
    }
}