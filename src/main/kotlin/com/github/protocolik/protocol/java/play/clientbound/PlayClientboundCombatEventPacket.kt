package com.github.protocolik.protocol.java.play.clientbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readString
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeString
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap

data class PlayClientboundCombatEventPacket
@JvmOverloads
constructor(
        var event: CombatEvent = CombatEvent.ENTER_COMBAT,
        var duration: Int = 0,
        var entityId: Int = 0,
        var playerId: Int = 0,
        var message: String = ""
) : Packet(PacketType.PLAY_CLIENTBOUND_COMBAT_EVENT) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        event = CombatEvent[byteBuf.readVarInt()]
        when (event) {
            CombatEvent.ENTER_COMBAT -> {
            }
            CombatEvent.END_COMBAT -> {
                duration = byteBuf.readVarInt()
                entityId = byteBuf.readInt()
            }
            CombatEvent.ENTITY_DEAD -> {
                playerId = byteBuf.readVarInt()
                entityId = byteBuf.readInt()
                message = byteBuf.readString()
            }
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeVarInt(event.id)
        when (event) {
            CombatEvent.ENTER_COMBAT -> {
            }
            CombatEvent.END_COMBAT -> {
                byteBuf.writeVarInt(duration)
                byteBuf.writeInt(entityId)
            }
            CombatEvent.ENTITY_DEAD -> {
                byteBuf.writeVarInt(playerId)
                byteBuf.writeInt(entityId)
                byteBuf.writeString(message)
            }
        }
    }

    enum class CombatEvent(val id: Int) {
        ENTER_COMBAT(0),
        END_COMBAT(1),
        ENTITY_DEAD(2);

        companion object {
            val values = values()
            private val byId = values.map { it.id to it }.toMap(Int2ObjectOpenHashMap())

            @JvmStatic
            operator fun get(id: Number) = byId[id.toInt()] ?: error("Unknown Combat Event id: $id")
        }
    }
}