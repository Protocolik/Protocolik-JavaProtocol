package com.github.protocolik.protocol.java.play.clientbound.spawn

import com.github.protocolik.api.minecraft.Position
import com.github.protocolik.api.minecraft.readPosition
import com.github.protocolik.api.minecraft.world.Painting
import com.github.protocolik.api.minecraft.writePosition
import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.*
import io.netty.buffer.ByteBuf
import java.util.*

data class PlayClientboundSpawnEntityPaintingPacket
@JvmOverloads
constructor(
        var entityId: Int = 0,
        var entityUuid: UUID = UUID(0, 0),
        var painting: Painting = Painting.KEBAB,
        var position: Position = Position(),
        var directionId: Int = 0
) : Packet(PacketType.PLAY_CLIENTBOUND_SPAWN_ENTITY_PAINTING) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        entityId = byteBuf.readVarInt()
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_13 -> {
                entityUuid = byteBuf.readUUID()
                painting = Painting[byteBuf.readVarInt()]
                position = byteBuf.readPosition(protocolVersion)
                directionId = byteBuf.readUnsignedByte().toInt()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                entityUuid = byteBuf.readUUID()
                painting = Painting[byteBuf.readString()]
                position = byteBuf.readPosition(protocolVersion)
                directionId = byteBuf.readUnsignedByte().toInt()
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                painting = Painting[byteBuf.readString()]
                position = byteBuf.readPosition(protocolVersion)
                directionId = byteBuf.readUnsignedByte().toInt()
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeVarInt(entityId)
        when {
            protocolVersion >= ProtocolVersion.RELEASE_1_13 -> {
                byteBuf.writeUUID(entityUuid)
                byteBuf.writeVarInt(painting.id)
                byteBuf.writePosition(position, protocolVersion)
                byteBuf.writeByte(directionId)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_9 -> {
                byteBuf.writeUUID(entityUuid)
                byteBuf.writeString(painting.namespace)
                byteBuf.writePosition(position, protocolVersion)
                byteBuf.writeByte(directionId)
            }
            protocolVersion >= ProtocolVersion.RELEASE_1_7 -> {
                byteBuf.writeString(painting.namespace)
                byteBuf.writePosition(position, protocolVersion)
                byteBuf.writeByte(directionId)
            }
        }
    }
}