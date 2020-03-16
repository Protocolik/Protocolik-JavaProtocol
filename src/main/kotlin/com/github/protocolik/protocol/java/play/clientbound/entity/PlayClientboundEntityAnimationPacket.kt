package com.github.protocolik.protocol.java.play.clientbound.entity

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap

data class PlayClientboundEntityAnimationPacket
@JvmOverloads
constructor(
        var entityId: Int = 0,
        var animation: Animation = Animation.SWING_MAIN_HAND
) : Packet(PacketType.PLAY_CLIENTBOUND_ENTITY_ANIMATION) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        entityId = byteBuf.readVarInt()
        animation = Animation[byteBuf.readUnsignedByte()]
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeVarInt(entityId)
        byteBuf.writeByte(animation.id)
    }

    enum class Animation(val id: Int) {
        SWING_MAIN_HAND(0),
        DAMAGE(1),
        LEAVE_BED(2),
        SWING_OFF_HAND(3),
        CRITICAL_HIT(4),
        ENCHANTMENT_CRITICAL_HIT(5);

        companion object {
            val values = values()
            private val byId = values.map { it.id to it }.toMap(Int2ObjectOpenHashMap())

            @JvmStatic
            operator fun get(id: Number) = byId[id.toInt()] ?: error("Unknown Animation id: $id")
        }
    }
}