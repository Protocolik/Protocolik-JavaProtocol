package com.github.protocolik.protocol.java.play.serverbound

import com.github.protocolik.api.protocol.Packet
import com.github.protocolik.api.protocol.PacketType
import com.github.protocolik.api.protocol.ProtocolVersion
import com.github.protocolik.api.utils.readString
import com.github.protocolik.api.utils.writeString
import io.netty.buffer.ByteBuf

data class PlayServerboundChatPacket
@JvmOverloads
constructor(
        var message: String = ""
) : Packet(PacketType.PLAY_SERVERBOUND_CHAT) {
    override fun read(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        message = byteBuf.readString()
    }

    override fun write(byteBuf: ByteBuf, protocolVersion: ProtocolVersion) {
        byteBuf.writeString(message)
    }
}