package com.github.protocolik.protocol.java.play.clientbound.spawn

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap

data class PlayClientboundSpawnEntityWeatherPacket
@JvmOverloads
constructor(
        var entityId: Int = 0,
        var type: EntityWeatherType = EntityWeatherType.LIGHTNING_BOLT,
        var x: Double = 0.0,
        var y: Double = 0.0,
        var z: Double = 0.0
) : Packet(PacketType.PLAY_CLIENTBOUND_SPAWN_ENTITY_WEATHER) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        entityId = byteBuf.readVarInt()
        type = EntityWeatherType[byteBuf.readByte()]
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
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeVarInt(entityId)
        byteBuf.writeByte(type.id)
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
    }

    enum class EntityWeatherType(val id: Int) {
        LIGHTNING_BOLT(1);

        companion object {
            val values = values()
            private val byId = values.map { it.id to it }.toMap(Int2ObjectOpenHashMap())

            @JvmStatic
            operator fun get(id: Number) = byId[id.toInt()] ?: error("Unknown Entity weather type with id: $id")
        }
    }
}