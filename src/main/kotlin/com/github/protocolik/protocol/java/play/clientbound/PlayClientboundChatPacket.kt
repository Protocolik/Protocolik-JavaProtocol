package com.github.protocolik.protocol.java.play.clientbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readString
import com.github.protocolik.api.utils.writeString
import io.netty.buffer.ByteBuf
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap

data class PlayClientboundChatPacket
@JvmOverloads
constructor(
        var message: String = "",
        var position: ChatPosition = ChatPosition.CHAT
) : Packet(PacketType.PLAY_CLIENTBOUND_CHAT) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        message = byteBuf.readString()
        if (protocolVersion >= ProtocolVersion.RELEASE_1_8) {
            position = ChatPosition[byteBuf.readByte()]
        }
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeString(message)
        if (protocolVersion >= ProtocolVersion.RELEASE_1_8) {
            byteBuf.writeByte(position.id)
        }
    }

    enum class ChatPosition(val id: Int) {
        CHAT(0),
        SYSTEM_MESSAGE(1),
        ACTION_BAR(2);

        companion object {
            val values = values()
            private val byId = values.map { it.id to it }.toMap(Int2ObjectOpenHashMap())

            @JvmStatic
            operator fun get(id: Number) = byId[id.toInt()] ?: error("Unknown Chat position id: $id")
        }
    }
}