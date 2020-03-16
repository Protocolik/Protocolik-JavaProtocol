package com.github.protocolik.protocol.java.play.serverbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readVarInt
import com.github.protocolik.api.utils.writeVarInt
import io.netty.buffer.ByteBuf
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap

data class PlayServerboundRequestPacket
@JvmOverloads
constructor(
        var request: Request = Request.RESPAWN
) : Packet(PacketType.PLAY_SERVERBOUND_REQUEST) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        request = Request[byteBuf.readVarInt()]
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeVarInt(request.id)
    }

    enum class Request(val id: Int) {
        RESPAWN(0),
        STATISTICS(1);

        companion object {
            val values = values()
            private val byId = values.map { it.id to it }.toMap(Int2ObjectOpenHashMap())

            @JvmStatic
            operator fun get(id: Number) = byId[id.toInt()] ?: error("Unknown Request with id: $id")
        }
    }
}